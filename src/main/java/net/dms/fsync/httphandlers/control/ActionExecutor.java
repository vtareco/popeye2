package net.dms.fsync.httphandlers.control;

import net.dms.fsync.httphandlers.entities.config.Action;
import net.dms.fsync.httphandlers.entities.ActionResponse;
import net.dms.fsync.httphandlers.entities.config.Execution;
import net.dms.fsync.httphandlers.entities.config.executions.ExecutionFactory;
import net.dms.fsync.httphandlers.entities.enumerations.HttpMethod;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.swing.components.Toast;
import net.dms.fsync.synchronizer.LocalVariables.business.VariableService;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by dminanos on 11/04/2017.
 */
public class ActionExecutor {
    private Map<String, ActionResponse> responses = new HashMap<String, ActionResponse>();
    private String executionFile;
    private Map<String, String> variables = new HashMap<String, String>();
    private VariableService vs = new VariableService();


    public ActionExecutor(String executionFile, Map<String, String> variables){
        this.executionFile = executionFile;
        this.variables = variables;
    }
    public ActionResponse  execute(){
        ActionResponse response = null;
        VariableService vs = new VariableService();
        try {
            InputStream is = Thread.class.getResourceAsStream(executionFile);


            JAXBContext jaxbContext = JAXBContext.newInstance(Execution.class);
            Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();

            String strExecution = IOUtils.toString(is, "UTF-8");
            strExecution = processVariables(strExecution);

           // Execution execution = (Execution)jaxbMarshaller.unmarshal(IOUtils.toInputStream(strExecution, "UTF-8"));
            Execution unmarshalExe = (Execution) jaxbMarshaller.unmarshal(IOUtils.toInputStream(strExecution, "UTF-8"));
            Execution execution = ExecutionFactory.createExecution(unmarshalExe);


            execution.initActions(vs.readJsonServerConf(WorkingJira.getJsonApplicationProperties()),vs.readJsonUserConf(WorkingJira.getJsonUserCreate()));
           System.out.println(execution);


           // SSLContext sslContext = new SSLContextBuilder()
             //       .loadTrustMaterial(ThreadLocal.class.getResource("/security/trust-store.jks"), "changeit".toCharArray()).build();


           // SSLContext sslContext = new SSLContextBuilder()
             //       .loadTrustMaterial(null, (certificate, authType) -> true).build();


    SSLContext sslContext = configureSSL();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();


            for(Action action: execution.getActions()){
                HttpHandler handler = new HttpHandler(action, httpClient, execution.getProxyConfiguration(), variables);
                response = handler.execute();
                variables.putAll(response.getVariables());
                responses.put(response.getName(), response);
            }
//PARA TESTAR FUNCIONALIDADE
            if(execution.getName().equals("fenix_upload_accs")){
                Toast  toast = new Toast("Successo", Toast.ToastType.INFO);
                toast.setVisible(true);
            }
            Toast  toast = new Toast("Successo v2", Toast.ToastType.INFO);
            toast.setVisible(true);

        }catch(Exception ex){

            throw new AppException(ex);
        }
        return response;
    }



    private String processVariables(String strExecution){
        for (String key : variables.keySet()){
            String mapValue = variables.get(key);
            System.out.println(">key:  " + key + ", value: " + mapValue);
            if ( strExecution != null) {
                try {
                    strExecution = strExecution.replaceAll("\\$\\{" + key + "\\}", mapValue != null ? Matcher.quoteReplacement(mapValue) : null);
                }catch(Exception ex){
                    System.out.println("error in process value");
                    ex.printStackTrace();
                    throw new AppException(ex);
                }
            }
        }
        System.out.println("value: " + strExecution);
        return strExecution;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    private static void test(){
        try {
            Execution execution = new Execution();
            List<Action> actions = new ArrayList<Action>();
            Action action = new Action();
            action.setHttpMethod(HttpMethod.GET);
            actions.add(action            );

            Action action2 = new Action();
            action2.setHttpMethod(HttpMethod.POST);
            actions.add(action2            );

            execution.setActions(actions);
            JAXBContext jaxbContext = JAXBContext.newInstance(Execution.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //jaxbMarshaller.marshal(customer, file);
            jaxbMarshaller.marshal(execution, System.out);

        } catch (JAXBException e) {
            throw new AppException(e);
        }

    }

    private SSLContext configureSSL() {
        try {
            //TODO FIXME - parametrizar
            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore ks = KeyStore.getInstance("JKS");

            ks.load(ThreadLocal.class.getResourceAsStream("/security/trust-store.jks"), "changeit".toCharArray());
            tmf.init(ks);


            // TODO FIXME - every connection should have its own keystore
            EverisConfig everisConfig = EverisConfig.getInstance();
            String keyFile = everisConfig.getProperty(EverisPropertiesType.JIRA_KEYSTORE_FILE);
            if (StringUtils.isNotEmpty(keyFile)) {
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                KeyStore kk = KeyStore.getInstance("PKCS12");
                String keyPassword = everisConfig.getProperty(EverisPropertiesType.JIRA_KEYSTORE_PASSWORD);
                 kk.load(ThreadLocal.class.getResourceAsStream(keyFile), keyPassword.toCharArray());
               // kk.load(new FileInputStream(keyFile), keyPassword.toCharArray());
                kmf.init(kk, keyPassword.toCharArray());
                sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
            }else{
                sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            }




           // sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

            return sslContext;

        } catch (Exception e) {
            throw new AppException(e);
        }
    }

}
