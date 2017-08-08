package net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 18/04/2017.
 */
public enum AccRowType {
    ID_ACC(0),
    NOMBRE(1),
    CODIGO_PETICION_CLIENTE(2),
    DESCRIPCION(3),
    ESTADO(4),
    TIPO(5),
    ID_PETICIONOT_ASOCIADA(6),
    RESPONSABLE(7),
    SUBTIPO(8),
    RECHAZOS_ENTREGA(9),

    CRITICIDAD(10),
    ESFUERZO(11),
    ESFUERZO_CLIENTE(12),
    FECHA_CREACION(13),
    FECHA_SOLICITUD_CLIENTE(14),
    FECHA_PREVISTA_PROYECTO(15),

    FECHA_ENTREGA(16),
    FECHA_CIERRE(18),
    FECHA_DESESTIMACION(19),
    FECHA_INICIO_CENTRO(20),
    RESULTADO_TESTING(21),
    PUNTOS_HISTORIA(22),
    HISTORIA_USUARIO(23),
    EPICA(24);

    private int colPosition;

    AccRowType(int colPosition) {
        this.colPosition = colPosition;
    }

    public int getColPosition() {
        return colPosition;
    }
}
