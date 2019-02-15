package net.dms.fsync.httphandlers.entities.config.executions;

import net.dms.fsync.httphandlers.entities.config.Action;
import net.dms.fsync.httphandlers.entities.config.Execution;
import net.dms.fsync.httphandlers.entities.config.Parameter;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;

public class JiraIssuesExecution extends  Execution{


    public JiraIssuesExecution(Execution execution) {
        setProxyConfiguration(execution.getProxyConfiguration());
        setActions(execution.getActions());
        setName(execution.getName());

    }

    @Override
    public void initActions(ApplicationProperties applicationProperties, UserChange userChange) {
        for (Action action: getActions()){
            if (action.getName().equals(WorkingJira.getLogIn())) {

                action.setUrl(applicationProperties.getJiraUrl() + WorkingJira.getJiraIssuesSearchLogInUrl());
                for (Parameter params : action.getParameters()) {

                    if (params.getName().equals(WorkingJira.getOsUser())) {
                        params.setValue(userChange.getJirauser());
                    } else if (params.getName().equals(WorkingJira.getOsPassword())) {
                        params.setValue(userChange.getJiraPassword());
                    }
                }
            } else if (action.getName().equals(WorkingJira.getJiraISsuesSearchRetrieveIssues())) {
                action.setUrl(applicationProperties.getJiraUrl() + action.getUrl().substring(action.getUrl().indexOf("/jira/rest")));
            }
        }
    }
}
