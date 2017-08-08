package net.dms.popeye.handlers.jfsynchronizer.control;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 18/04/2017.
 */
public class EverisConfig {
    private static EverisConfig ourInstance = new EverisConfig();
    private Properties properties;
    private Map<String, String> mapResponsableJiraEveris = new HashMap<>();
    private Map<String, String> jiraFilters = new HashMap<>();

    public static EverisConfig getInstance() {
        return ourInstance;
    }

    private EverisConfig() {
        properties = new Properties();
        try {

            properties.load(Thread.class.getResourceAsStream("/bmw/rsp/everis.conf"));
            Set<String> responseblesKeys = properties.stringPropertyNames().stream().filter(k -> k.startsWith(EverisPropertiesType.RESPONSABLE_JIRA.getProperty())).collect(Collectors.toSet());
            for(String key : responseblesKeys) {
                mapResponsableJiraEveris.put(properties.getProperty(key), key.replace(EverisPropertiesType.RESPONSABLE_JIRA.getProperty()+ ".", ""));
            }

            Set<String> jiraFiltrosKeys = properties.stringPropertyNames().stream().filter(k -> k.startsWith(EverisPropertiesType.JIRA_FILTROS.getProperty())).collect(Collectors.toSet());
            for(String key : jiraFiltrosKeys) {
                jiraFilters.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(EverisPropertiesType key){
        return properties.getProperty(key.getProperty());
    }

    public Map<String, String> getMapResponsableJiraEveris() {
        return mapResponsableJiraEveris;
    }

    public String searchEverisEmployIdByJiraId(String jiraId){
        return mapResponsableJiraEveris.get(jiraId);
    }

    public Map<String, String> getJiraFilters(){
        return jiraFilters;
    }
}
