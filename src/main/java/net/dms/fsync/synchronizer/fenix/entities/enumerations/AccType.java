package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 19/04/2017.
 */
public enum AccType implements VisualizableType{
    EVOLUTIVO("Evolutivo (ENP)"),
    CORRECCION_INCIDENCIAS("Corrección Incidencias"),
    USER_STORY("User Story"),
    MEETING("Meeting"),
    SCRUM_MASTER("Scrum "),
    EXTRA_TASK("Extra-Task"),
    BUG("BUG");

    private String description;

    AccType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
