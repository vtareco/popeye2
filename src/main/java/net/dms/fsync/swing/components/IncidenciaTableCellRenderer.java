package net.dms.fsync.swing.components;


import net.dms.fsync.swing.models.IncidenciaTableModel;
import net.dms.fsync.synchronizer.fenix.entities.FenixIncidencia;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.IncidenciaRowType;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

public class IncidenciaTableCellRenderer extends JenixTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        FenixIncidencia incidencia = ((IncidenciaTableModel) table.getModel()).getList().get(row);

        switch (IncidenciaRowType.values()[column]) {
         /*   case FECHA_FIN:
               // String fechaFin = String.valueOf(incidencia.getFechaFin());
                if (incidencia.getFechaFin() == null) {
                    c.setBackground(MyColors.TABLE_FIELD_REQUIRED);
                    break;
                }*/
        }

        return c;
    }
}