package net.dms.fsync.synchronizer.LocalVariables.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserChange implements Serializable {

	private String jiraPassword;
	private String jirauser;
	private String fenixPassword;
	private String fenixUser;

	public UserChange(String jiraPassword, String jirauser, String fenixPassword, String fenixUser) {
		this.jiraPassword = jiraPassword;
		this.jirauser = jirauser;
		this.fenixPassword = fenixPassword;
		this.fenixUser = fenixUser;
	}

	public UserChange() {
	}

	public String getJiraPassword() {
		return jiraPassword;
	}

	public void setJiraPassword(String jiraPassword) {
		this.jiraPassword = jiraPassword;
	}

	public String getJirauser() {
		return jirauser;
	}

	public void setJirauser(String jirauser) {
		this.jirauser = jirauser;
	}

	public String getFenixPassword() {
		return fenixPassword;
	}

	public void setFenixPassword(String fenixPassword) {
		this.fenixPassword = fenixPassword;
	}

	public String getFenixUser() {
		return fenixUser;
	}

	public void setFenixUser(String fenixUser) {
		this.fenixUser = fenixUser;
	}

	public void getFenixUser(String s) {
	}
}
