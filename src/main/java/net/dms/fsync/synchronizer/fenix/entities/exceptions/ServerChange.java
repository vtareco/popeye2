package net.dms.fsync.synchronizer.fenix.entities.exceptions;

public class ServerChange {

	String jiraServer;
	String fenixServer;

	public ServerChange(String jiraServer, String fenixServer) {
		this.jiraServer = jiraServer;
		this.fenixServer = fenixServer;
	}

	public String getJiraServer() {
		return jiraServer;
	}

	public void setJiraServer(String jiraServer) {
		this.jiraServer = jiraServer;
	}

	public String getFenixServer() {
		return fenixServer;
	}

	public void setFenixServer(String fenixServer) {
		this.fenixServer = fenixServer;
	}
}
