package net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 19/04/2017.
 */
public enum AccType implements VisualizableType{
    EVOLUTIVO("Evolutivo (ENP)"),
    CORRECCION_INCIDENCIAS("Corrección Incidencias");

    private String description;

    AccType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
