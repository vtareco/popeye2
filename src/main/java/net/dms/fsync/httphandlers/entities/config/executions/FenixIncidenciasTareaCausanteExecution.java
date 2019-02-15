package net.dms.fsync.httphandlers.entities.config.executions;

import net.dms.fsync.httphandlers.entities.config.Action;
import net.dms.fsync.httphandlers.entities.config.Execution;
import net.dms.fsync.swing.dialogs.AccDialog;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;

public class FenixIncidenciasTareaCausanteExecution extends Execution {

    public FenixIncidenciasTareaCausanteExecution(Execution execution) {
        setProxyConfiguration(execution.getProxyConfiguration());
        setActions(execution.getActions());
        setName(execution.getName());
    }

    @Override
    public void initActions(ApplicationProperties applicationProperties, UserChange userChange) {
        for (Action action: getActions()){
            if (action.getName().equals(WorkingJira.getMetaInfo())){
                action.setUrl(applicationProperties.getFenixUrl() + action.getUrl().substring(action.getUrl().indexOf("/fenix/requerimientos")));
            }
        }
    }
}
