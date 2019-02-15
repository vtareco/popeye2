package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by vics on 05/02/2019.
 */
public enum DudaEstadoType implements VisualizableType{
    ABIERTA("Abierta"),
    RESPONDIDA("Respondida"),
    SOLUCIONADA("Solucionada"),
    CANCELADA("Cancelada");

    private String description;

    DudaEstadoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
