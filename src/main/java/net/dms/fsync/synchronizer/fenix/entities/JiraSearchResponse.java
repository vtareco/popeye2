package net.dms.fsync.synchronizer.fenix.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dminanos on 14/04/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSearchResponse implements Serializable {
    private List<JiraIssue> issues;

    public List<JiraIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<JiraIssue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "JiraSearchResponse{" +
                "issues=" + issues +
                '}';
    }
}
