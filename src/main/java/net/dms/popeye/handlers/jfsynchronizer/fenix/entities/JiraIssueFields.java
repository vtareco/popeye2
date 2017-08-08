package net.dms.popeye.handlers.jfsynchronizer.fenix.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dminanos on 14/04/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueFields {
    private Assignee assignee;
    private Status status;
    private String summary;
    private String description;

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "JiraIssueFields{" +
                "assignee=" + assignee +
                ", status=" + status +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
