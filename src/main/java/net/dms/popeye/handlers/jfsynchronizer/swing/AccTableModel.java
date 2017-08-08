package net.dms.popeye.handlers.jfsynchronizer.swing;

import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixAcc;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.AccStatus;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.TableColumnEnumType;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.MyColors;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.MyTableModel;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dminanos on 19/05/2017.
 */
public class AccTableModel extends MyTableModel<FenixAcc, AccTableModel.Columns> {

    public enum Columns implements TableColumnEnumType{

        ID_ACC(false), NOMBRE(true), DESCRIPCION(true), CODIGO_PETICION_CLIENTE(true), ESTADO(true), TIPO(true), SUB_TIPO(true), ESFUERZO(true), INCURRIDO(false), RESPONSABLE(true), FECHA_PREVISTA_PROYECTO(false);

        private boolean editable;

        Columns(boolean editable) {
            this.editable = editable;
        }

        public boolean isEditable() {
            return editable;
        }

        public static Columns lookup(int iPosition){
            return Arrays.stream(Columns.values()).filter( c -> c.ordinal() == iPosition).findFirst().get();
        }
    }

    public AccTableModel(List<FenixAcc> accs){
        super(Columns.class, accs);
    }



    public boolean isCellEditable(int row, int col)
    {
        FenixAcc acc = rows.get(row);

        return Columns.lookup(col).isEditable() && (AccStatus.EN_EJECUCION.getDescription().equals(acc.getEstado()) || AccStatus.PENDIENTE_ASIGNACION.getDescription().equals(acc.getEstado()));

    }
    public void setValueAt(Object value, int row, int col) {
        FenixAcc acc = rows.get(row);
        switch (Columns.lookup(col)){
            case ESFUERZO:
                acc.setEsfuerzo((String)value);
                acc.setEsfuerzoCliente(acc.getEsfuerzo());
                fireTableCellUpdated(row, col);
                break;
            case ESTADO:
                acc.setEstado((String)value);
                if (AccStatus.ENTREGADA.getDescription().equals(acc.getEstado()) && acc.getFechaEntrega() == null){
                    acc.setFechaEntrega(new Date());
                }
                fireTableCellUpdated(row, col);
                break;
            default:
                super.setValueAt(value, row, col);

        }
    }

    public Color getRowColour(int row) {

        FenixAcc acc = rows.get(row);
        if (AccStatus.ENTREGADA.getDescription().equals(acc.getEstado())
                ||AccStatus.DESESTIMADA.getDescription().equals(acc.getEstado())
                ||AccStatus.CERRADA.getDescription().equals(acc.getEstado())){
            return MyColors.ACC_NO_EDITABLE;
        }else{
            return super.getRowColour(row);
        }

    }



}