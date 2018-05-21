package net.dms.fsync.swing.components;



import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccStatus;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by dminanos on 23/04/2017.
 */
public class AccStatusRenderer  extends JComboBox implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            JTable table, Object color,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        for (AccStatus s : AccStatus.values()){
            this.addItem(s.getDescription());
        }

        return this;
    }
}
