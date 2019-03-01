package net.dms.fsync.synchronizer.fenix.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dminanos on 14/04/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssue implements Serializable {

	private String key;
	private JiraIssueFields fields;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public JiraIssueFields getFields() {
		return fields;
	}

	public void setFields(JiraIssueFields fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "JiraIssue{" +
			   "key='" + key + '\'' +
			   ", fields=" + fields +
			   '}';
	}
}
