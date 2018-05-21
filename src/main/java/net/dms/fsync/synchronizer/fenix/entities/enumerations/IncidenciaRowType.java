package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 23/05/2017.
 */
public enum IncidenciaRowType {
    ID_INCIDENCIA,
    ID_PETICION_OT,
    NOMBRE_INCIDENCIA,
    LOCALIZADA_EN,
    TIPO_INCIDENCIA,
    DESCRIPCION,
    FECHA_INICIO,
    FECHA_FIN,
    ESFUERZO_HH,
    URGENCIA,
    IMPACTO,
    RESUELTA_POR_CLIENTE,
    PRIORIDAD,
    FECHA_PREVISTA_CENTRO,
    TAREA_CAUSANTE,
    OT_CORRECTOR,
    ACC_CORRECTOR,
    ESTADO;

    public int getColPosition(){
        return this.ordinal();
    }
}
