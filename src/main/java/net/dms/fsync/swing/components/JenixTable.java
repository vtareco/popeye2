package net.dms.fsync.swing.components;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;


/**
 * Created by dminanos on 22/05/2017.
 */
public class JenixTable<M extends JenixTableModel, R> extends JTable{
    public JenixTable(JenixTableModel tm) {
        super(tm);
        configure();
    }

    public JenixTable() {
        super();
        configure();
    }


    public M getModel(){
        return (M)super.getModel();
    }

    public List getList() {
        return this.getModel().getList();
    }

    public void addRow(R r){
        getList().add(r);
        getModel().fireTableDataChanged();
    }

    public void configure(){

        setFillsViewportHeight(true);
        setAutoCreateRowSorter(true);
        setOpaque(true);
        setRowSelectionAllowed(true);
        setColumnSelectionAllowed(false);
        setSelectionBackground(Color.GREEN);

        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setDefaultRenderer(Object.class, new JenixTableCellRenderer());
        setDefaultRenderer(Date.class, new DateCellRenderer());

        SwingUtil.agregarMenu(this, new JMenuItem[]{SwingUtil.menuCopiar(this)});

    }
}
