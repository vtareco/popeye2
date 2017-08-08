package net.dms.popeye.handlers.jfsynchronizer.bussiness;

import net.dms.popeye.handlers.jfsynchronizer.control.JiraRepository;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.JiraSearchResponse;


/**
 * Created by dminanos on 17/04/2017.
 */
public class JiraService {
    private JiraRepository jiraRepository = new JiraRepository();
    public JiraSearchResponse search(String jql){

        JiraSearchResponse response = jiraRepository.search(jql);

        return response;

    }
}
