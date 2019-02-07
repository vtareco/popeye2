package net.dms.fsync.synchronizer.LocalVariables.entities;

public class WorkingJira {
    private  static String jiraBmw = "jira.bmw";
    private static String jiraFilterProperty = "everis.conf.jira.filtro.";


    public static String getJiraFilterProperty() {
        return jiraFilterProperty;
    }

    public static String getJiraBmw() { return jiraBmw; }
}
