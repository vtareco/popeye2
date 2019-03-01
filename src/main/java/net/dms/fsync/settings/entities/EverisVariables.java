package net.dms.fsync.settings.entities;

/**
 * Created by dminanos on 20/04/2017.
 */
public enum EverisVariables {
	FENIX_TIMESTAMP("everis.fenix.timestamp"),
	FENIX_ID_PETICION_OT("everis.fenix.idpeticionot"),
	FENIX_USER(EverisPropertiesType.FENIX_USER.getProperty()),
	FENIX_PASSWORD(EverisPropertiesType.FENIX_PASSWORD.getProperty()),
	FENIX_URL_BASE(EverisPropertiesType.FENIX_URL_BASE.getProperty()),
	UPLOAD_FILE("everis.fenix.upload.file"),
	JIRA_JQL("everis.jira.jql"),
	JIRA_USER(EverisPropertiesType.JIRA_USER.getProperty()),
	JIRA_PASSWORD(EverisPropertiesType.JIRA_PASSWORD.getProperty()),
	JIRA_URL_BASE(EverisPropertiesType.JIRA_URL_BASE.getProperty()),
	WORKING_DIRECTORY(EverisPropertiesType.WORKING_DIRECTORY.getProperty()),
	PROXY_HOST(EverisPropertiesType.PROXY_HOST.getProperty()),
	PROXY_PORT(EverisPropertiesType.PROXY_PORT.getProperty());
	private String variableName;

	EverisVariables(String variableName) {
		this.variableName = variableName;
	}

	public String getVariableName() {
		return variableName;
	}
}
