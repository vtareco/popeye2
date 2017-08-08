package net.dms.popeye.handlers.jfsynchronizer.control;

/**
 * Created by dminanos on 20/04/2017.
 */
public enum EverisVariables {
    TIMESTAMP("everis.fenix.timestamp"),
    ID_PETICION_OT("everis.fenix.idpeticionot"),
    UPLOAD_FILE("everis.fenix.upload.file"),
    JIRA_JQL("everis.jira.jql")
    ;

    private String variableName;

    EverisVariables(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}
