package net.dms.fsync.httphandlers.entities.config.executions;

import net.dms.fsync.httphandlers.entities.config.Execution;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;

public class ExecutionFactory {

    public static Execution createExecution(Execution execution) {
        System.out.println(execution + "OLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        if (execution.getName().equals(WorkingJira.getFenixDownloadDudas())) {
            execution = new FenixDownloadDudaExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getFenixLogin())) {
            execution = new FenixLoginExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getJiraIssuesSearch())) {
            execution = new JiraIssuesExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getFenix_Upload_incidencias())) {
            execution = new FenixUploadIncidenciaExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getFenixUploadAccs())) {
            execution = new FenixUploadAccExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getFenixMetaInfoIncidencias())) {
            execution = new FenixIncidenciasTareaCausanteExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getFenixDownloadIncidencias())) {
            execution = new FenixDownloadIncidenciasExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getFenixDownloadAccsIncurridos())) {
            execution = new FenixDownloadAccIncurridoExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getFenixDownloadAccs())) {
            execution = new FenixDownloadAccExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getExtranetLogin())) {
            execution = new ExtranetLoginExecution(execution);
        } else if (execution.getName().equals(WorkingJira.getAuthJiraIssuesSearch())) {
            execution = new AUTHJiraIssesSearchExecution(execution);
        }

        return execution;
    }


}