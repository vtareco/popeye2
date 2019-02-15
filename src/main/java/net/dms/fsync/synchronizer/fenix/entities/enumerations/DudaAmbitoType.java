package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by vics on 05/02/2019.
 */
public enum DudaAmbitoType implements VisualizableType{
    INTERNO("Interno"),
    EXTERNO("Externo");

    private String description;

    DudaAmbitoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
