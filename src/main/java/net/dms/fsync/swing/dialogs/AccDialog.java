package net.dms.fsync.swing.dialogs;

import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import net.dms.fsync.synchronizer.fenix.entities.FenixResponsable;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.*;
import net.dms.fsync.swing.components.JenixDialog;
import net.dms.fsync.swing.components.JenixTable;
import net.dms.fsync.swing.components.NumberCellEditor;
import net.dms.fsync.swing.components.SwingUtil;
import net.dms.fsync.swing.models.FenixResponsablesTableModel;
import net.dms.fsync.settings.business.SettingsService;
import net.dms.fsync.settings.entities.Actor;
import net.sourceforge.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.*;
import java.util.List;


public class AccDialog extends JenixDialog<FenixAcc> {
    private JTextField txtNombre;
    private JTextArea txaDescripcion;
    private JTextField txtCodigoPeticionCliente;
    private JComboBox cmbEstado;
    private JComboBox cmbTipo;
    private JComboBox cmbSubTipo;
    private JTextField txtPuntosHistoria;

    private JTextField txtHistoriaUsuario;
    private JenixTable<FenixResponsablesTableModel, FenixResponsable> jtbResponsables;
    private JScrollPane responsablesScrollPane;
    private JDatePicker datFechaPrevistaProyecto;
    private JTextField txtEsfuerzoCliente;




    public AccDialog(Component parent, FenixAcc initialPayload) {
        super(parent, initialPayload);
        // TODO FIXME, move to abstract class
        this.setSize(550, 650);
        setLocationRelativeTo(parent);
        setTitle("ACC");
        setModal(true);
        setResizable(false);
        setVisible(true);
    }

    @Override
    protected void loadData() {
        SwingUtil.loadComboBox(AccStatus.class, cmbEstado, true);
        SwingUtil.loadComboBox(AccType.class, cmbTipo, true);
        SwingUtil.loadComboBox(AccSubType.class, cmbSubTipo, true);
    }

    @Override
    public void edit() {
        txtNombre.setText(getPayload().getNombre());
        txaDescripcion.setText(getPayload().getDescripcion());
        txtCodigoPeticionCliente.setText(getPayload().getCodigoPeticionCliente());
        cmbEstado.setSelectedItem(getPayload().getEstado());
        cmbTipo.setSelectedItem(getPayload().getTipo());
        cmbSubTipo.setSelectedItem(getPayload().getSubTipo());
        txtPuntosHistoria.setText(getPayload().getPuntosHistoria());
        txtHistoriaUsuario.setText(getPayload().getHistoriaUsuario());
txtEsfuerzoCliente.setText(getPayload().getEsfuerzoCliente());
        List<FenixResponsable> responsablesEsfuerzos = jtbResponsables.getModel().getList();

        for (Actor actor : SettingsService.getInstance().getSettings().getActores()){
            responsablesEsfuerzos.add(new FenixResponsable(null, actor.getNombre(), actor.getNumeroEmpleadoEveris()));
        }

        String[] responsables = getPayload().getResponsable() != null ? getPayload().getResponsable().split("-") : new String[0];
        String[] esfuerzos = getPayload().getEsfuerzo() != null ? getPayload().getEsfuerzo().split("-") : new String[0];

        for (int i = 0; i < responsables.length; i++){
            final int index = i;
            FenixResponsable fenixResponsable = responsablesEsfuerzos.stream().filter(r -> r.getNumero().equals(responsables[index])).findFirst().orElse(null);
            if (fenixResponsable != null){
                fenixResponsable.setEsfuerzo(Double.parseDouble(esfuerzos[index]));
            }else {
                fenixResponsable = new FenixResponsable(Double.parseDouble(esfuerzos[index]), null, responsables[index]);
                responsablesEsfuerzos.add(fenixResponsable);
            }
        }


    }

    @Override
    public JComponent createCentralPanel() {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new GridBagLayout());

        JLabel lblSummary = new JLabel("Nombre");
        JLabel lblDescription = new JLabel("Descripción");
JLabel lblCodigoPeticionCliente = new JLabel("Código petición cliente");
JLabel lblEstado = new JLabel("Estado");
JLabel lblTipo = new JLabel("Tipo");
JLabel lblSubtipo = new JLabel("Subtipo");
JLabel lblPuntosHistoria = new JLabel("Puntos Historia");
JLabel lblEsfuerzo = new JLabel("Esfuerzo cliente");
JLabel lblHistoriaUsuario  = new JLabel("Historia usuario");
JLabel lblResponsables = new JLabel("Responsables");
JLabel lblFechaPrevistaProyecto = new JLabel("Fecha prevista");


        txtCodigoPeticionCliente = new JTextField();
        cmbEstado = new JComboBox();
        cmbTipo = new JComboBox();
        cmbSubTipo = new JComboBox();
        txtPuntosHistoria = new JTextField();
        txtHistoriaUsuario = new JTextField();
        txtEsfuerzoCliente = new JTextField();

        FenixResponsablesTableModel responsablesTableModel = new FenixResponsablesTableModel(new ArrayList<FenixResponsable>());
        jtbResponsables = new JenixTable(responsablesTableModel);
        responsablesScrollPane = new JScrollPane(jtbResponsables, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        responsablesScrollPane.setMinimumSize(new Dimension(200,180));
        jtbResponsables.setFillsViewportHeight(true);

        responsablesTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                StringBuilder responsables = new StringBuilder();
                StringBuilder esfuerzos = new StringBuilder();
                calculateResponsalblesEsfuerzos(responsables, esfuerzos);
                txtEsfuerzoCliente.setText(Double.toString(FenixAcc.calculateTotalEsfuerzo(esfuerzos.toString())));
            }
        });
        jtbResponsables.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        jtbResponsables.getColumnModel().getColumn(FenixResponsablesTableModel.Columns.ESFUERZO.ordinal()).setCellEditor(new NumberCellEditor());

       // datFechaPrevistaProyecto = new JDatePickerImpl();

        txtNombre = new JTextField(15);
        txaDescripcion = new JTextArea();
        txaDescripcion.setRows(10);
        txaDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane sp = new JScrollPane(txaDescripcion, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        sp.setPreferredSize(new Dimension(300, 200));
        sp.setMinimumSize(new Dimension(300, 200));
       // sp.setBounds(txtSummary.getBounds());



        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        int fila = 0;
        double anchoEntiquetas = 0.1;

        constraints.gridx = 0;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblSummary, constraints);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(txtNombre, constraints);

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblDescription, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(sp, constraints);

        // fields codigo petición - historia usuario
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblCodigoPeticionCliente, constraints);
        constraints.weightx = 1;
        constraints.gridwidth = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblHistoriaUsuario, constraints);
        constraints.weightx = 1;


        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(txtCodigoPeticionCliente, constraints);


        constraints.gridx = 1;
        constraints.gridy = fila;
        panel.add(txtHistoriaUsuario, constraints);


        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblEstado, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(cmbEstado, constraints);

        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblTipo, constraints);
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblSubtipo, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(cmbTipo, constraints);


        constraints.gridx = 1;
        constraints.gridy = fila;
        panel.add(cmbSubTipo, constraints);

        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblPuntosHistoria, constraints);
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblEsfuerzo, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(txtPuntosHistoria, constraints);

        constraints.gridx = 1;
        constraints.gridy = fila;
        panel.add(txtEsfuerzoCliente, constraints);




        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblResponsables, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.gridwidth = 2;
        constraints.gridheight = 5;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(responsablesScrollPane, constraints);



        return panel;
    }

    @Override
    public void fillPayLoad() {
        getPayload().setDescripcion(txaDescripcion.getText());
        getPayload().setNombre(txtNombre.getText());

        getPayload().setCodigoPeticionCliente(txtCodigoPeticionCliente.getText());
        getPayload().setEstado((String)cmbEstado.getSelectedItem());
        getPayload().setTipo((String)cmbTipo.getSelectedItem());
        getPayload().setSubTipo((String)cmbSubTipo.getSelectedItem());
        getPayload().setPuntosHistoria(txtPuntosHistoria.getText());

        getPayload().setHistoriaUsuario(txtHistoriaUsuario.getText());
        getPayload().setEsfuerzoCliente(txtEsfuerzoCliente.getText());



        StringBuilder responsables = new StringBuilder();
        StringBuilder esfuerzos = new StringBuilder();
        calculateResponsalblesEsfuerzos(responsables, esfuerzos);

        getPayload().setEsfuerzo(esfuerzos.toString());
        getPayload().setResponsable(responsables.toString());

    }

    @Override
    public void initialize() {

    }

    private void calculateResponsalblesEsfuerzos(StringBuilder responsables, StringBuilder esfuerzos){
        List<FenixResponsable> fenixResponsables = jtbResponsables.getList();

        for (FenixResponsable fenixResponsable : fenixResponsables){
            if (fenixResponsable.getEsfuerzo() != null) {
                if (responsables.length() > 0) {
                    responsables.append("-");
                    esfuerzos.append("-");
                }
                responsables.append(fenixResponsable.getNumero());
                esfuerzos.append(fenixResponsable.getEsfuerzo());
            }
        }
    }


}
