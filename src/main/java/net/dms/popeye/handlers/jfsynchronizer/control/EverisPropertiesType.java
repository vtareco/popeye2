package net.dms.popeye.handlers.jfsynchronizer.control;

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
    JIRA_FILTRO_BY_ID("byId"),
    INCIDENCIAS_FILE_NAME("everis.conf.incidencias.file.name");

    private String property;

    EverisPropertiesType(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
