package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 24/05/2017.
 */
public enum IncidenciaEstadoType implements VisualizableType{
    EN_ANALISIS("En an�lisis"),
    EN_EJECUCION("En ejecuci�n"),
    ENTREGADA("Entregada"),
    ENTREGA_RECHAZADA("Entrega Rechazada"),
    CERRADA("Cerrada"),
    DESESTIMADA("Desestimada"),
    CANCELADA("Cancelada"),
    APLAZADA("Aplazada");

    private String description;

    IncidenciaEstadoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
