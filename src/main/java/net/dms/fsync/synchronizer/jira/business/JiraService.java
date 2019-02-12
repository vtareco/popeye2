package net.dms.fsync.synchronizer.jira.business;

import net.dms.fsync.synchronizer.jira.control.JiraRepository;
import net.dms.fsync.synchronizer.fenix.entities.JiraSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by dminanos on 17/04/2017.
 */
@Service
public class JiraService {
    @Autowired
    private JiraRepository jiraRepository;

    public JiraSearchResponse search(String jql){


        JiraSearchResponse response = jiraRepository.search(jql);

        return response;

    }
}
