package net.dms.fsync.httphandlers.entities.config.executions;

import net.dms.fsync.httphandlers.entities.config.Action;
import net.dms.fsync.httphandlers.entities.config.Execution;
import net.dms.fsync.httphandlers.entities.config.Header;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;

public class FenixUploadIncidenciaExecution extends Execution {

    public FenixUploadIncidenciaExecution(Execution execution) {
        setProxyConfiguration(execution.getProxyConfiguration());
        setActions(execution.getActions());
        setName(execution.getName());
    }

    @Override
    public void initActions(ApplicationProperties applicationProperties, UserChange userChange) {
        for (Action action : getActions()){
            if(action.getName().equals(WorkingJira.getUpload())){
                action.setUrl(applicationProperties.getFenixUrl() + WorkingJira.getFenixUploadincidenciasUploadUrl());
                for (Header headers : action.getHeaders()) {
                    if (headers.getName().equals(WorkingJira.getHeaderOrigin())) {
                        headers.setValue(applicationProperties.getFenixUrl());
                    } else if (headers.getName().equals(WorkingJira.getHeaderReferer())) {
                        headers.setValue(applicationProperties.getFenixUrl() + WorkingJira.getFenixUploadincidenciasUploadReferer());
                    }
                }
            }
        }
    }
}
