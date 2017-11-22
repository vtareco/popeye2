package net.dms.popeye.settings.entities;

/**
 * Created by dminanos on 19/11/2017.
 */
public class Settings {
    private AccSettings accSettings;
    private JiraSettings jiraSettings;
    private String incidenciasFileName;
    private String peticionesFolderPattern;
    private String projectPath;
    private ConnectionSettings connectionSettings;

    public ConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }

    public void setConnectionSettings(ConnectionSettings connectionSettings) {
        this.connectionSettings = connectionSettings;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public AccSettings getAccSettings() {
        return accSettings;
    }

    public void setAccSettings(AccSettings accSettings) {
        this.accSettings = accSettings;
    }

    public JiraSettings getJiraSettings() {
        return jiraSettings;
    }

    public void setJiraSettings(JiraSettings jiraSettings) {
        this.jiraSettings = jiraSettings;
    }

    public String getIncidenciasFileName() {
        return incidenciasFileName;
    }

    public void setIncidenciasFileName(String incidenciasFileName) {
        this.incidenciasFileName = incidenciasFileName;
    }

    public String getPeticionesFolderPattern() {
        return peticionesFolderPattern;
    }

    public void setPeticionesFolderPattern(String peticionesFolderPattern) {
        this.peticionesFolderPattern = peticionesFolderPattern;
    }
}

