package net.dms.popeye.handlers.jfsynchronizer.swing.components;

import net.dms.popeye.handlers.jfsynchronizer.swing.EverisManager;
import net.dms.popeye.handlers.jfsynchronizer.swing.IncidenciaTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;


/**
 * Created by dminanos on 22/05/2017.
 */
public class MyJTable<M extends MyTableModel, R> extends JTable{
    public MyJTable(MyTableModel tm) {
        super(tm);
        configure();
    }

    public MyJTable() {
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

    private void configure(){

        setFillsViewportHeight(true);
        setAutoCreateRowSorter(true);
        setOpaque(true);
        setRowSelectionAllowed(true);
        setColumnSelectionAllowed(false);
        setSelectionBackground(Color.GREEN);

        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setDefaultRenderer(Object.class, new MyTableCellRenderer());
        setDefaultRenderer(Date.class, new DateCellRenderer());

        SwingUtil.agregarMenu(this, new JMenuItem[]{SwingUtil.menuCopiar(this)});
    }
}
