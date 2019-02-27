package net.dms.fsync.synchronizer.fenix.entities.enumerations;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

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
            if (StringUtils.isBlank(description)){
                return null;
            }
            else{
               // return Arrays.stream(AccStatus.values()).filter(c -> c.description.equals(description)).findFirst().get();
                Optional<AccStatus> first = Arrays.stream(AccStatus.values()).filter(c -> c.description.equals(description)).findFirst();
                return first.orElse(PENDIENTE_ASIGNACION);
            }
        }catch (Exception ex){
            throw ex;
        }
    }
}
