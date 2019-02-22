package net.dms.fsync.swing.components;


import net.dms.fsync.swing.models.DudaTableModel;
import net.dms.fsync.synchronizer.fenix.entities.FenixDuda;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.DudaRowType;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

public class DudaTableCellRenderer extends JenixTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //DudaTableModel model = (DudaTableModel) table.getModel();
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        FenixDuda duda = ((DudaTableModel) table.getModel()).getList().get(row);

        switch (DudaRowType.values()[column]){
            case DESCRIPCION:
               if(StringUtils.isBlank(duda.getDescripcion())){
                   c.setBackground(MyColors.TABLE_FIELD_REQUIRED);
                   break;
               }

                }


        return c;
    }
}