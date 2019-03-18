package net.dms.fsync.swing.Panes;

import net.dms.fsync.swing.components.DateFormat;
import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Properties;

public class IncidenciasDatas extends JPanel {

    private JLabel txtDataInicio;
    public JDatePickerImpl dataInicio;
    private JLabel txtDataFim;
    public JDatePickerImpl dataFim;
    private JLabel txtDataCentro;
    public JDatePickerImpl dataCentro;


    public IncidenciasDatas() {
        setLayout(null);
        loadPane();
    }

    public void loadPane(){
        labelsComponents();

        datePickers();
    }

    private void labelsComponents() {
        txtDataInicio = new JLabel();
        txtDataInicio.setText("Data Inicio*");
        txtDataInicio.setBounds(27, 27, 86, 14);
        add(txtDataInicio);

        txtDataFim = new JLabel();
        txtDataFim.setText("Data Fim*");
        txtDataFim.setBounds(286, 27, 86, 14);
        add(txtDataFim);

        txtDataCentro = new JLabel();
        txtDataCentro.setText("Data de Centro*");
        txtDataCentro.setBounds(27, 122, 96, 14);
        add(txtDataCentro);
    }

    private void datePickers() {
        UtilDateModel modelDataInicio = new UtilDateModel();
        JDatePanelImpl datainicio = new JDatePanelImpl(modelDataInicio);
        dataInicio = new JDatePickerImpl(datainicio,new DateFormat());
        dataInicio.setBounds(27, 52, 179, 23);
        add(dataInicio);

        UtilDateModel modelDataFim = new UtilDateModel();
        JDatePanelImpl datafim = new JDatePanelImpl(modelDataFim);
        dataFim = new JDatePickerImpl(datafim,new DateFormat());
        dataFim.setBounds(286, 52, 180, 23);
        add(dataFim);

        UtilDateModel modelDataCentro = new UtilDateModel();
        JDatePanelImpl datacentro = new JDatePanelImpl(modelDataCentro);
        dataCentro = new JDatePickerImpl(datacentro,new DateFormat());
        dataCentro.setBounds(27, 147, 179, 23);
        add(dataCentro);
    }

}
