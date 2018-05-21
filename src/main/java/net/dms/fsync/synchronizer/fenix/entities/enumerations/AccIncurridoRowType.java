package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 26/04/2017.
 */
public enum AccIncurridoRowType {

    ID_ACC,
    ID_JIRA,
    NOMBRE,
    TIPO,
    SUBTIPO,
    PET_ASOCIADA,
    OT_ASOCIADA,
    ESTADO,
    RESPONSABLE,
    FECHA_SOLICICITUD_CLIENTE,
    ESFUERZO,
    INCURRIDO,
    ETC,
    FECHA_INICIO_CENTRO,
    RESULTADO_TESTING;

    public int getColPosition() {
        return this.ordinal();
    }
}
