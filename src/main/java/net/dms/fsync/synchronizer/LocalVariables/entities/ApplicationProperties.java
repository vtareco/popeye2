package net.dms.fsync.synchronizer.LocalVariables.entities;

public class ApplicationProperties {
    String fenixUrl;
    String jiraUrl;
    String proxyHost;
    String proxyPort;
    String workingDirectory;


    public ApplicationProperties(String fenixUrl, String jiraUrl, String proxyHost, String proxyPort, String workingDirectory) {
        this.fenixUrl = fenixUrl;
        this.jiraUrl = jiraUrl;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.workingDirectory = workingDirectory;
    }

    public ApplicationProperties() {

    }




    public String getFenixUrl() {
        return fenixUrl;
    }

    public void setFenixUrl(String fenixUrl) {
        this.fenixUrl = fenixUrl;
    }

    public String getJiraUrl() {
        return jiraUrl;
    }

    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}
