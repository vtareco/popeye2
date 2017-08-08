package net.dms.popeye.handlers.jfsynchronizer.swing.components;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;

/**
 * Created by dminanos on 24/05/2017.
 */
public class DateCellRenderer extends MyTableCellRenderer {
    public DateCellRenderer() { super(); }

    @Override
    public void setValue(Object value) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        setText((value == null) ? "" : sdf.format(value));
    }
}