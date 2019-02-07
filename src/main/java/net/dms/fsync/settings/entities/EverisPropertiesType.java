package net.dms.fsync.settings.entities;

/**
 * Created by dminanos on 18/04/2017.
 */
public enum EverisPropertiesType {
    PROJECT_PATH("everis.conf.project.path"),
    ACC_FILE_NAME("everis.conf.acc.file.name"),
    ACC_INCURRIDOS_FILE_NAME("everis.conf.acc.incurridos.file.name"),
    PETICIONES_FOLDER_PATTERN("everis.conf.peticiones.folder.pattern"),
    RESPONSABLE_JIRA("everis.conf.responsable.jira"),
    JIRA_FILTROS("everis.conf.jira.filtro"),
    JIRA_FILTRO_BY_ID("everis.conf.jira.filtro.byId"),
    FENIX_USER("everis.conf.fenix.user"),
    FENIX_PASSWORD("everis.conf.fenix.password"),
    FENIX_URL_BASE("everis.conf.fenix.urlbase"),
    JIRA_KEYSTORE_FILE("everis.conf.jira.keystore.file"),
    JIRA_KEYSTORE_PASSWORD("everis.conf.jira.keystore.password"),
    JIRA_USER("everis.conf.jira.user"),
    JIRA_PASSWORD("everis.conf.jira.password"),
    JIRA_URL_BASE("everis.conf.jira.urlbase"),
    INCIDENCIAS_FILE_NAME("everis.conf.incidencias.file.name"),
    /*AQUI*/
    DUDAS_FILE_NAME("everis.conf.dudas.file.name"),
    PROXY_HOST("everis.conf.proxyConfiguration.host"),
    PROXY_PORT("everis.conf.proxyConfiguration.port"),
    WORKING_DIRECTORY("everis.conf.project.path");


    private String property;

    EverisPropertiesType(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }}
