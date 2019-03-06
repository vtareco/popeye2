package net.dms.fsync.synchronizer.jira.control;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dms.fsync.httphandlers.control.ActionExecutor;
import net.dms.fsync.httphandlers.entities.ActionResponse;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.settings.entities.EverisVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.fenix.entities.JiraSearchResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dminanos on 14/04/2017.
 */
@Component
public class JiraRepository {
    public JiraSearchResponse search(String jql) {

        try {
            jql = URLEncoder.encode(jql, "UTF-8");
            Map<String, String> variables = createJiraVariables();
            variables.put(EverisVariables.JIRA_JQL.getVariableName(), jql);
            ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/jira_issues_search.xml", variables);
            ActionResponse ar = ae.execute();


            HttpResponse httpResponse = (HttpResponse)ar.getResponse();
            HttpEntity entity2 = httpResponse.getEntity();
            InputStream in = entity2.getContent();
            String content = IOUtils.toString(in);
            System.out.println("content: " + content);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


            // Convert JSON string from file to Object

            JiraSearchResponse response = mapper.readValue(content, JiraSearchResponse.class);
            System.out.println(response);

           return response;

        }catch(Exception ex){
            if(ex.getCause().getMessage().equals("HTTP Error, HTTP Status: 400")){
                throw new AppException("A Query pode estar incorreta!?");
            }

            throw new AppException(ex);
        }
    }

    private Map<String, String> createJiraVariables(){
        Map<String, String> variables = new HashMap<String, String>();
        variables.put(EverisVariables.JIRA_URL_BASE.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.JIRA_URL_BASE));
        variables.put(EverisVariables.JIRA_USER.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.JIRA_USER));
        variables.put(EverisVariables.JIRA_PASSWORD.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.JIRA_PASSWORD));
        return variables;
    }
}
