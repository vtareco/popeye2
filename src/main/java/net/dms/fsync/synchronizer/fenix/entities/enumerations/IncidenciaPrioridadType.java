package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 24/05/2017.
 */
public enum IncidenciaPrioridadType implements VisualizableType {
    CRITICA("CRITICA"),
    ALTA("ALTA"),
    MEDIA("MEDIA"),
    BAJA("BAJA"),
    MUY_BAJA("MUY_BAJA");

    private String description;

    IncidenciaPrioridadType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
