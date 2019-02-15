package net.dms.fsync.httphandlers.entities.config.executions;

import net.dms.fsync.httphandlers.entities.config.Action;
import net.dms.fsync.httphandlers.entities.config.Execution;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;

import java.nio.file.Watchable;

public class FenixDownloadAccIncurridoExecution extends Execution {
    public FenixDownloadAccIncurridoExecution(Execution execution) {
        setProxyConfiguration(execution.getProxyConfiguration());
        setActions(execution.getActions());
        setName(execution.getName());
    }

    @Override
    public void initActions(ApplicationProperties applicationProperties, UserChange userChange) {
        for (Action action:getActions()){
            if (action.getName().equals(WorkingJira.getDownload())){
                action.setUrl(applicationProperties.getFenixUrl()+ action.getUrl().substring(action.getUrl().indexOf("/fenix/actuaciones/")));
            }
        }
    }
}
