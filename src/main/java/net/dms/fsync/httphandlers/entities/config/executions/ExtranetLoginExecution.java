package net.dms.fsync.httphandlers.entities.config.executions;

import net.dms.fsync.httphandlers.entities.config.Action;
import net.dms.fsync.httphandlers.entities.config.Execution;
import net.dms.fsync.httphandlers.entities.config.Parameter;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;

public class ExtranetLoginExecution extends Execution {

    public ExtranetLoginExecution(Execution execution) {
        setProxyConfiguration(execution.getProxyConfiguration());
        setActions(execution.getActions());
        setName(execution.getName());
    }

    @Override
    public void initActions(ApplicationProperties applicationProperties, UserChange userChange) {
        for (Action action: getActions()){

            if (action.getName().equals(WorkingJira.getLogIn())){
                for(Parameter parameter : action.getParameters()){
                    if (parameter.getName().equals(WorkingJira.getUser())){
                        parameter.setValue(userChange.getFenixUser());
                    }
                    else if (parameter.getName().equals(WorkingJira.getPassword())){
                        parameter.setValue(userChange.getFenixPassword());
                    }
                }
            }
        }
    }
}
