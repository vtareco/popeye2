package net.dms.fsync.swing.models;

import net.dms.fsync.settings.Internationalization;
import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccStatus;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;
import net.dms.fsync.swing.components.MyColors;
import net.dms.fsync.swing.components.JenixTableModel;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dminanos on 19/05/2017.
 */
public class AccTableModel extends JenixTableModel<FenixAcc, AccTableModel.Columns> {

    public enum Columns implements TableColumnEnumType{

        ID_ACC(false, 70, Internationalization.getStringTranslated("accId")),
        NOMBRE(true, 250, Internationalization.getStringTranslated("nameUper")),
        CODIGO_PETICION_CLIENTE(true, 80, Internationalization.getStringTranslated("codePetitionClientUper")),
        ESTADO(true, 80, Internationalization.getStringTranslated("statUper")),
        JIRA_STATUS(true, 50, Internationalization.getStringTranslated("jiraStatus")),
        TIPO(true, 80, Internationalization.getStringTranslated("typeUPER")),
        SUB_TIPO(true, 80, Internationalization.getStringTranslated("subTypeUper")),
        ESFUERZO(false, 80, Internationalization.getStringTranslated("effortUper")),
        INCURRIDO(false, 80, Internationalization.getStringTranslated("incurred")),
        PUNTOS_HISTORIA(false, 80, Internationalization.getStringTranslated("storyPoints")),
        HISTORIA_USUARIO(true, 80, Internationalization.getStringTranslated("userHistoryUper")),

        ETC(true, 50, Internationalization.getStringTranslated("estimate")),
        PORCENTAJE_COMPLETADO(true, 30, Internationalization.getStringTranslated("percentageCompleted")),
        RESPONSABLE(true, 100, Internationalization.getStringTranslated("responsible")),
        FECHA_PREVISTA_PROYECTO(true, 80, Internationalization.getStringTranslated("projectDatePlanened")),
        DESCRIPCION(true, 250, Internationalization.getStringTranslated("descriptionUper")),
        ULTIMA_BITACORA(false, 50, Internationalization.getStringTranslated("lastBinnacle")),
        BITACORA(false, 250, Internationalization.getStringTranslated("binnacle")),
        ID_PETICION(false, 50, Internationalization.getStringTranslated("petitionId"));


        private final static int WIDTH_M = 80;
        private boolean editable;
        private int width;
        private String columnName;

        Columns(boolean editable, int width,String columnName) {
            this.editable = editable;
            this.width = width;
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }

        public boolean isEditable() {
            return editable;
        }

        public int getWidth(){
            return width;
        }

        public static Columns lookup(int iPosition){
            return Arrays.stream(Columns.values()).filter( c -> c.ordinal() == iPosition).findFirst().get();
        }
    }

    @Override
    protected String getValueToDisplay(Columns enumC) {
        return enumC.getColumnName();
    }

    public AccTableModel(List<FenixAcc> accs){
        super(Columns.class, accs);
    }



    public boolean isCellEditable(int row, int col)
    {
        FenixAcc acc = rows.get(row);

        return Columns.lookup(col).isEditable() && (AccStatus.EN_EJECUCION.getDescription().equals(acc.getEstado()) || AccStatus.PENDIENTE_ASIGNACION.getDescription().equals(acc.getEstado()));

    }

    public Object getValueAt(int row, int col) {
        FenixAcc acc = rows.get(row);
        switch (Columns.lookup(col)) {
            case BITACORA:
                if (!acc.getBitacora().isEmpty()){
                    return acc.getBitacora().get(acc.getBitacora().size()-1).getComment();
                } else {
                    return null;
                }
            case ULTIMA_BITACORA:
                if (!acc.getBitacora().isEmpty()){
                    return acc.getBitacora().get(acc.getBitacora().size()-1).getCreationDate();
                } else {
                    return null;
                }
            default:
                return super.getValueAt(row, col);
        }

    }


    public void setValueAt(Object value, int row, int col) {
        FenixAcc acc = rows.get(row);
        switch (Columns.lookup(col)){
            case ESFUERZO:
                acc.setEsfuerzo((String)value);
                acc.setEsfuerzoCliente(acc.getEsfuerzo());
                fireTableCellUpdated(row, col);
                break;
            case ID_PETICION:
                acc.setIdPeticion((String)value);
                acc.setIdPeticion(acc.getIdPeticion());
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