package net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 19/04/2017.
 */
public enum AccStatus implements VisualizableType{
    EN_EJECUCION("En Ejecución"),
    ENTREGADA("Entregada"),
    CERRADA("Cerrada"),
    DESESTIMADA("Desestimada"),
    PENDIENTE_ASIGNACION("Pendiente Asignación");

    private String description;

    AccStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
