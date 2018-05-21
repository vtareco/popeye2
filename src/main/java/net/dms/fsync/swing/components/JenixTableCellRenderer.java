package net.dms.fsync.swing.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dminanos on 22/05/2017.
 */
public class JenixTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            c.setBackground(Color.BLUE);
            c.setForeground(Color.WHITE);
        }else{
            c.setBackground(row % 2 == 0 ? MyColors.TABLE_ROW_EVEN :  MyColors.TABLE_ROW_ODD);
            c.setForeground(Color.BLACK);
        }
        return c;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            setText((value == null) ? "" : sdf.format(value));
        }else if(value instanceof Double){
            if (value != null) {
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                setText(nf.format(((Double) value).doubleValue()));
            }
        }else{
            super.setValue(value);
        }
    }


}