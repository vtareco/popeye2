package net.dms.fsync.swing;

import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

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
