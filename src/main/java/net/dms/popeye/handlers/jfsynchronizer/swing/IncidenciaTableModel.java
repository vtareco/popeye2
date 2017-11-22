package net.dms.popeye.handlers.jfsynchronizer.swing;

import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixIncidencia;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.TableColumnEnumType;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.JenixTableModel;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.SelectOption;

import java.util.List;

/**
 * Created by dminanos on 22/05/2017.
 */
public class IncidenciaTableModel extends JenixTableModel<FenixIncidencia, IncidenciaTableModel.Columns> {

    public enum Columns implements TableColumnEnumType {

       ID_INCIDENCIA(false),
       NOMBRE_INCIDENCIA(true),
        LOCALIZADA_EN(true),
        TIPO_INCIDENCIA(true),
        DESCRIPCION(true),
        FECHA_INICIO(true),
        FECHA_FIN(true),
        ESFUERZO_HH(true),
        URGENCIA(true),
        IMPACTO(true),
        PRIORIDAD(true),
        FECHA_PREVISTA_CENTRO(true),
        TAREA_CAUSANTE(true),
        OT_CORRECTOR(true),
        ACC_CORRECTOR(true),
        ESTADO(true);


        private boolean editable;

        Columns(boolean editable) {
            this.editable = editable;
        }

        public boolean isEditable() {
            return editable;
        }

        public int getWidth(){
            return 50;
        }
    }




    public IncidenciaTableModel(List<FenixIncidencia> incidencias){
        super(Columns.class, incidencias);
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Columns c = findColumnTypeByOrdinal(columnIndex);
        FenixIncidencia r = getList().get(rowIndex);
        switch (findColumnTypeByOrdinal(c.ordinal())){
            case ACC_CORRECTOR:
                 return new SelectOption<>(r.getAccCorrector(), r.getAccCorrector());
            case TAREA_CAUSANTE:
                return new SelectOption<>(r.getTareaCausante(), r.getTareaCausante());
            default:
                return super.getValueAt(rowIndex,columnIndex);
        }


    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        FenixIncidencia incidencia = rows.get(row);
        switch (findColumnTypeByOrdinal(col)){
            case ACC_CORRECTOR:
                if (value != null) {
                    SelectOption<String,String> opt = (SelectOption)value;
                    incidencia.setAccCorrector(opt == null ? null : opt.getCode());
                }
                break;
            case TAREA_CAUSANTE:
                if (value != null) {
                    SelectOption<String,String> opt = (SelectOption)value;
                    incidencia.setTareaCausante(opt == null ? null : opt.getCode());
                }
                break;
            default:
                    super.setValueAt(value, row, col);
        }
    }


}