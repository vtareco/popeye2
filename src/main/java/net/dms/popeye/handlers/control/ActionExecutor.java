package net.dms.popeye.handlers.control;

import net.dms.popeye.handlers.entities.config.Action;
import net.dms.popeye.handlers.entities.ActionResponse;
import net.dms.popeye.handlers.entities.config.Execution;
import net.dms.popeye.handlers.entities.enumerations.HttpMethod;
import net.dms.popeye.handlers.entities.exceptions.AppException;
import org.apache.commons.io.IOUtils;
import org.apache.http.ParseException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dminanos on 11/04/2017.
 */
public class ActionExecutor {
    private Map<String, ActionResponse> responses = new HashMap<String, ActionResponse>();
    private String executionFile;
    private Map<String, String> variables = new HashMap<String, String>();



    public ActionExecutor(String executionFile, Map<String, String> variables){
        this.executionFile = executionFile;
        this.variables = variables;
    }
    public ActionResponse  execute(){
        ActionResponse response = null;
        try {
            InputStream is = Thread.class.getResourceAsStream(executionFile);


            JAXBContext jaxbContext = JAXBContext.newInstance(Execution.class);
            Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();

            String strExecution = IOUtils.toString(is, "UTF-8");
            strExecution = processVariables(strExecution);

            Execution execution = (Execution)jaxbMarshaller.unmarshal(IOUtils.toInputStream(strExecution, "UTF-8"));
            System.out.println(execution);

            CloseableHttpClient httpClient = HttpClients.createDefault();


            for(Action action: execution.getActions()){
                HttpHandler handler = new HttpHandler(action, httpClient, execution.getProxyConfiguration(), variables);
                response = handler.execute();
                variables.putAll(response.getVariables());
                responses.put(response.getName(), response);
            }

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
                    strExecution = strExecution.replaceAll("\\$\\{" + key + "\\}", mapValue);
                }catch(ParseException ex){
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

            File file = new File("C:\\file.xml");
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

}
