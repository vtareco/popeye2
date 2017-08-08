package net.dms.popeye.handlers.jfsynchronizer.swing.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by dminanos on 22/05/2017.
 */
public class MyTableCellRenderer extends DefaultTableCellRenderer {
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
}