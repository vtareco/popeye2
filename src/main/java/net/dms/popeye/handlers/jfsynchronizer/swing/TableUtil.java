package net.dms.popeye.handlers.jfsynchronizer.swing;

import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.TableColumnEnumType;

import javax.swing.*;

/**
 * Created by dminanos on 17/11/2017.
 */
public class TableUtil {

    public static void configureColumnWidths(JTable table, Class<? extends TableColumnEnumType> columns){
       int i = 0;
       for (TableColumnEnumType c : columns.getEnumConstants()){
           table.getColumnModel().getColumn(i++).setPreferredWidth(c.getWidth());
       }
    }


}
