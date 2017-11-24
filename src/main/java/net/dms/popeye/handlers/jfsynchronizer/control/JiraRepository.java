package net.dms.popeye.handlers.jfsynchronizer.control;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dms.popeye.handlers.control.ActionExecutor;
import net.dms.popeye.handlers.entities.ActionResponse;
import net.dms.popeye.handlers.entities.exceptions.AppException;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.JiraSearchResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dminanos on 14/04/2017.
 */
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
