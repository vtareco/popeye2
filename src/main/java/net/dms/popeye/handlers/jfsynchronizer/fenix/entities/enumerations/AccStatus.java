package net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by dminanos on 19/04/2017.
 */
public enum AccStatus implements VisualizableType{
    EN_EJECUCION("En Ejecución", false),
    ENTREGADA("Entregada", true),
    CERRADA("Cerrada", true),
    DESESTIMADA("Desestimada", true),
    PENDIENTE_ASIGNACION("Pendiente Asignación", false),
    INACABADA("Inacabada", false);

    private boolean isFinal;

    private String description;

    AccStatus(String description, boolean isFinal) {
        this.description = description;
        this.isFinal = isFinal;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public static AccStatus lookup(String description){
        try {
            return StringUtils.isBlank(description) ? null : Arrays.stream(AccStatus.values()).filter(c -> c.description.equals(description)).findFirst().get();
        }catch (Exception ex){
            throw ex;
        }
    }
}
