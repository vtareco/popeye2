package net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 24/05/2017.
 */
public enum IncidenciaImpactoType implements VisualizableType {
    BLOQUEANTE("BLOQUEANTE"),
    MEDIO("MEDIO"),
    BAJO("BAJO");

    private String description;

    IncidenciaImpactoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
