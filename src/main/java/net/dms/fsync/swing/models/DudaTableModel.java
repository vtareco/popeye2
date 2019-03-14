package net.dms.fsync.swing.models;

import net.dms.fsync.settings.Internationalization;
import net.dms.fsync.swing.components.JenixTableModel;
import net.dms.fsync.swing.components.MyColors;
import net.dms.fsync.swing.components.SwingUtil;
import net.dms.fsync.synchronizer.fenix.entities.FenixDuda;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.DudaRowType;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * Created by vics on 05/02/2019.
 */
public class DudaTableModel extends JenixTableModel<FenixDuda, DudaTableModel.Columns> {

    public enum Columns implements TableColumnEnumType {

        ID_DUDA(false, 50, Internationalization.getStringTranslated("doubtId")),
        ID_REQUERIMIENTO(false, 50, Internationalization.getStringTranslated("requirementID")),
        ESTADO(true, 50, Internationalization.getStringTranslated("statUper")),
        ACC(true, 50, Internationalization.getStringTranslated("accUper")),
        DESCRIPCION(true, 50, Internationalization.getStringTranslated("responseUper")),
        RESPUESTA(true, 50, Internationalization.getStringTranslated("responseUper")),
        RESP_RESPUESTA_PROYECTO(true, 50, Internationalization.getStringTranslated("responseResponseProject")),
        RESP_RESPUESTA_CLIENTE(true, 50, Internationalization.getStringTranslated("responseResponseClient")),
        RESPONSABLE_CONSULTA(true, 50, Internationalization.getStringTranslated("responsableConsult")),
        FECHA_PREVISTA_RESPUESTA(true, 50, Internationalization.getStringTranslated("responseExpectedDAte")),
        AGRUPACION(true, 50, Internationalization.getStringTranslated("agrupacion")),
        ID_RELACIONADA(true, 50, Internationalization.getStringTranslated("relatedId")),
        AMBITO(true, 50, Internationalization.getStringTranslated("ambit")),
        CRITICIDAD(true, 50, Internationalization.getStringTranslated("criticality")),
        F_LOCALIZADA(true, 250, Internationalization.getStringTranslated("fLocalized")),
        RELATIVA_A(true, 50, Internationalization.getStringTranslated("relativeA")),
        DOC_INCOMP(true, 250, Internationalization.getStringTranslated("incompleteDocumentation")),
        AUTOR_ULT_ACT(true, 80, Internationalization.getStringTranslated("actorLastUpdate")),
        CREADOR(true, 80, Internationalization.getStringTranslated("creator")),
        FECHA_ALTA(true, 50, Internationalization.getStringTranslated("highDate")),
        FECHA_ULT_ACT(true, 80, Internationalization.getStringTranslated("dateLastUpdate")),
        ID_OT(true, 80, Internationalization.getStringTranslated("otId"));
        private int width;
        private String columnName;
        private boolean editable;

        Columns(boolean editable, int width, String columnName) {
            this.editable = editable;
            this.width = width;
            this.columnName = columnName;
        }


        public boolean isEditable() {
            return editable;
        }

        public int getWidth() {
            return width;
        }

        public String getColumnName() {
            return columnName;
        }
    }


    public DudaTableModel(List<FenixDuda> duda) {
        super(Columns.class, duda);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FenixDuda duda = rows.get(rowIndex);
        switch (DudaRowType.values()[columnIndex]) {
            case ID_DUDA:
                return duda.getIdDuda();
            case ID_REQUERIMIENTO:
                return duda.getIdRequerimiento();
            case ACC:
                return duda.getAcc();
            case FECHA_ALTA:
                return duda.getFechaAlta();
            case ESTADO:
                return duda.getEstado();
            case DESCRIPCION:

                return duda.getDescripcion();
            case RESPUESTA:
                return duda.getRespuesta();
            case FECHA_PREVISTA_RESPUESTA:
                return duda.getFechaPrevistaRespuesta();
            case RESP_RESPUESTA_PROYECTO:
                return duda.getRespRespuestaProyecto();
            case RESP_RESPUESTA_CLIENTE:
                return duda.getRespRespuestaCliente();
            case RESPONSABLE_CONSULTA:
                return duda.getResponsableConsulta();
            case AGRUPACION:
                return duda.getAgrupacion();
            case ID_RELACIONADA:
                return duda.getIdRelacionada();
            case AMBITO:
                return duda.getAmbito();
            case CRITICIDAD:
                return duda.getCriticidad();
            case F_LOCALIZADA:
                return duda.getFLocalizada();
            case RELATIVA_A:
                return duda.getRelativaA();
            case DOC_INCOMP:
                return duda.getDocIncomp();
            case FECHA_ULT_ACT:
                return duda.getFechaUltAct();
            case AUTOR_ULT_ACT:
                return duda.getAutorUltAct();
            case CREADOR:
                return duda.getCreador();
            case ID_OT:
                return duda.getIdOt();
        }
        return null;
    /*    Columns c = findColumnTypeByOrdinal(columnIndex);
        FenixDuda d = getList().get(rowIndex);
        switch (findColumnTypeByOrdinal(c.ordinal())){
            case ESTADO:
                return new SelectOption<>(d.getEstado(), d.getEstado());
            default:
                return super.getValueAt(rowIndex,columnIndex);
        }*/
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        FenixDuda duda = rows.get(row);
        switch (DudaRowType.values()[col]) {
            case ID_DUDA:
                duda.setIdDuda((Long) value);
                duda.setIdDuda(duda.getIdDuda());
                fireTableCellUpdated(row, col);
                break;
            case ID_REQUERIMIENTO:
                if (StringUtils.isBlank(value.toString())) {
                    SwingUtil.msgErrorTxt("Meko meko");
                    return;
                }
                duda.setIdRequerimiento(Long.valueOf(value.toString()));
                duda.setIdRequerimiento(duda.getIdRequerimiento());
                fireTableCellUpdated(row, col);
                break;
            case ACC:
                duda.setAcc((String) value);
                duda.setAcc(duda.getAcc());
                fireTableCellUpdated(row, col);
                break;
            case FECHA_ALTA:
                duda.setFechaAlta((Date) value);
                duda.setFechaAlta(duda.getFechaAlta());
                fireTableCellUpdated(row, col);
                break;
            case ESTADO:
                duda.setEstado((String) value);
                duda.setEstado(duda.getEstado());
                fireTableCellUpdated(row, col);
                break;
            case DESCRIPCION:
                duda.setDescripcion((String) value);
                duda.setDescripcion(duda.getDescripcion());
                fireTableCellUpdated(row, col);
                break;
            case RESPUESTA:
                duda.setRespuesta((String) value);
                duda.setRespuesta(duda.getRespuesta());
                fireTableCellUpdated(row, col);
                break;
            case FECHA_PREVISTA_RESPUESTA:
                duda.setFechaPrevistaRespuesta((Date) value);
                duda.setFechaPrevistaRespuesta(duda.getFechaPrevistaRespuesta());
                fireTableCellUpdated(row, col);
                break;
            case RESP_RESPUESTA_PROYECTO:
                duda.setRespRespuestaProyecto((String) value);
                duda.setRespRespuestaProyecto(duda.getRespRespuestaProyecto());
                fireTableCellUpdated(row, col);
                break;
            case RESP_RESPUESTA_CLIENTE:
                duda.setRespRespuestaCliente((String) value);
                duda.setRespRespuestaCliente(duda.getRespRespuestaCliente());
                fireTableCellUpdated(row, col);
                break;
            case RESPONSABLE_CONSULTA:
                duda.setResponsableConsulta((String) value);
                duda.setResponsableConsulta(duda.getResponsableConsulta());
                fireTableCellUpdated(row, col);
                break;
            case AGRUPACION:
                duda.setAgrupacion((String) value);
                duda.setAgrupacion(duda.getAgrupacion());
                fireTableCellUpdated(row, col);
                break;
            case ID_RELACIONADA:
                duda.setIdRelacionada((Long) value);
                duda.setIdRelacionada(duda.getIdRelacionada());
                fireTableCellUpdated(row, col);
                break;
            case AMBITO:
                duda.setAmbito((String) value);
                duda.setAmbito(duda.getAmbito());
                fireTableCellUpdated(row, col);
                break;
            case CRITICIDAD:
                duda.setCriticidad((String) value);
                duda.setCriticidad(duda.getCriticidad());
                fireTableCellUpdated(row, col);
                break;
            case F_LOCALIZADA:
                duda.setFLocalizada((String) value);
                duda.setFLocalizada(duda.getFLocalizada());
                fireTableCellUpdated(row, col);
                break;
            case RELATIVA_A:
                duda.setRelativaA((String) value);
                duda.setRelativaA(duda.getRelativaA());
                fireTableCellUpdated(row, col);
                break;
            case DOC_INCOMP:
                duda.setDocIncomp((String) value);
                duda.setDocIncomp(duda.getDocIncomp());
                fireTableCellUpdated(row, col);
                break;
            case FECHA_ULT_ACT:
                duda.setFechaUltAct((Date) value);
                duda.setFechaUltAct(duda.getFechaUltAct());
                fireTableCellUpdated(row, col);
                break;
            case AUTOR_ULT_ACT:
                duda.setAutorUltAct((String) value);
                duda.setAutorUltAct(duda.getAutorUltAct());
                fireTableCellUpdated(row, col);
                break;
            case CREADOR:
                duda.setCreador((String) value);
                duda.setCreador(duda.getCreador());
                fireTableCellUpdated(row, col);
                break;

            case ID_OT:
                duda.setIdOt((String) value);
                duda.setIdOt(duda.getIdOt());
                fireTableCellUpdated(row, col);
                break;
        }

      /*  FenixDuda duda = rows.get(row);
        switch (findColumnTypeByOrdinal(col)){
            case ESTADO:
                if (value != null) {
                    SelectOption<String,String> opt = (SelectOption)value;
                    duda.setEstado(opt == null ? null : opt.getCode());
                }
                break;
            default:
                super.setValueAt(value, row, col);
        }*/

    }

    @Override
    protected String getValueToDisplay(Columns enumC) {
        return enumC.getColumnName();
    }

    /* if(StringUtils.isBlank(txtDudaDescription.getText())){
           txtDudaDescription.setBackground(MyColors.TABLE_FIELD_REQUIRED);
       }*/

}
