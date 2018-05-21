package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 19/04/2017.
 */
public enum AccCriticidad implements VisualizableType{
    CRITICA("Crítica"),
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");

    private String description;

    AccCriticidad(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
