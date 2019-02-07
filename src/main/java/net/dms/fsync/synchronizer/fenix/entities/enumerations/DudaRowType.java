package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by vics on 06/02/2019.
 */
public enum DudaRowType {
    ID_DUDA,
    ID_REQUERIMIENTO,
    ESTADO,
    ACC,
    DESCRIPCION,
    RESPUESTA,
    RESP_RESPUESTA_PROYECTO,
    RESP_RESPUESTA_CLIENTE,
    RESPONSABLE_CONSULTA,
    FECHA_PREVISTA_RESPUESTA,
    AGRUPACION,
    ID_RELACIONADA,
    CRITICIDAD,
    F_LOCALIZADA,
    RELATIVA_A,
    DOC_INCOMP,
    AUTOR_ULT_ACT,
    CREADOR,
    FECHA_ALTA,
    FECHA_ULT_ACT;

    public int getColPosition(){
        return this.ordinal();
    }
}
