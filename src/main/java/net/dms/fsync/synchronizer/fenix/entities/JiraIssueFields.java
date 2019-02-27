package net.dms.fsync.synchronizer.fenix.entities;

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
    private int   storypoints;
    private JiraIssue parent;

    public int getStorypoints() {
        return storypoints;
    }

    public void setStorypoints(int storypoints) {
        this.storypoints = storypoints;
    }

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

    public JiraIssue getParent() {
        return parent;
    }

    public void setParent(JiraIssue parent) {
        this.parent = parent;
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
