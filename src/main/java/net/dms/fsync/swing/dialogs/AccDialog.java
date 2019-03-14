package net.dms.fsync.swing.dialogs;

import net.dms.fsync.httphandlers.common.Utils;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.Internationalization;
import net.dms.fsync.settings.business.SettingsService;
import net.dms.fsync.settings.entities.Actor;
import net.dms.fsync.swing.components.*;
import net.dms.fsync.swing.models.BitacoraTableModel;
import net.dms.fsync.swing.models.FenixResponsablesTableModel;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.OtInfo;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import net.dms.fsync.synchronizer.fenix.entities.Bitacora;
import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import net.dms.fsync.synchronizer.fenix.entities.FenixResponsable;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccStatus;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccSubType;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccType;
import net.sourceforge.jdatepicker.JDatePicker;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AccDialog extends JenixDialog<FenixAcc> {
    private JTextField txtNombre;
    private JTextArea txaDescripcion;


    private JTextField txtCodigoPeticionCliente;
    private JTextField txtIdPeticion;


    private JComboBox cmbEstado;
    private JComboBox cmbTipo;
    // private JComboBox cmbSubTipo;
    private JTextField txtPuntosHistoria;
    private JTextArea txaComments;
    private JenixTable<BitacoraTableModel, Bitacora> jtbBitacora;

    private JTextField txtHistoriaUsuario;
    private JenixTable<FenixResponsablesTableModel, FenixResponsable> jtbResponsables;
    private JScrollPane responsablesScrollPane;
    private JScrollPane bitacoraScrollPane;
    private JDatePicker datFechaPrevistaProyecto;
    private JTextField txtEsfuerzoCliente;


    public AccDialog(Component parent, FenixAcc initialPayload) {
        super(parent, initialPayload);
        // TODO FIXME, move to abstract class
        this.setSize(550, 820);
        setLocationRelativeTo(parent);
        setTitle("ACC");
        setModal(true);
        setResizable(false);
        setVisible(true);
    }

    @Override
    protected void loadData() {
        SwingUtil.loadComboBox(AccStatus.class, cmbEstado, true);
        //getPayload().setEstado(AccStatus.PENDIENTE_ASIGNACION.getDescription()); //PENDIENTE
        SwingUtil.loadComboBox(AccType.class, cmbTipo, true);
        //  SwingUtil.loadComboBox(AccSubType.class, cmbSubTipo, true);
    }


    @Override
    protected void onAccept() {
        super.onAccept();
        LocalVariables lv = new LocalVariables();
        String codigopeticion = lv.readOtInfoFile(WorkingJira.getIdOt()).getCodigoPeticionCliente();
        if(cmbEstado.getSelectedItem().equals(AccStatus.PENDIENTE_ASIGNACION.getDescription())){
            getPayload().setEstado(AccStatus.EN_EJECUCION.getDescription());
        }
        updateCodigoPeticion(codigopeticion);
        updateCodigoPeticionCliente();
    }

    @Override
    public void edit() {

       /* LocalVariables lv = new LocalVariables();
        String idpeticion = lv.readOtInfoFile(WorkingJira.getIdOt());
        txtIdPeticion.setText(idpeticion);*/

        /*ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        String projectPath = ap.getWorkingDirectory();*/
        LocalVariables lv = new LocalVariables();
        String idpeticion = lv.readOtInfoFile(WorkingJira.getIdOt()).getId_peticion();
        txtNombre.setText(getPayload().getNombre());
        txaDescripcion.setText(getPayload().getDescripcion());
        txtIdPeticion.setText(idpeticion);
        //txtCodigoPeticionCliente.setText(getPayload().getCodigoPeticionCliente());

        // txtCodigoPeticionCliente.setText(getPayload().getCodigoPeticionCliente());


        if (StringUtils.isBlank(txtIdPeticion.getText())) {
            Toast.display(Internationalization.getStringTranslated("toastFillIdPetition"), Toast.ToastType.ERROR);
        }

        verif();
        //txtCodigoPeticionCliente.setText(getPayload().getCodigoPeticionCliente());

        //txtCodigoPeticionCliente.setText("BMW.SA3.SPRINT");

        // txtIdPeticion.setText(getPayload().getIdOt());


        cmbEstado.setSelectedItem(getPayload().getEstado());
        cmbTipo.setSelectedItem(getPayload().getTipo());
        //   cmbSubTipo.setSelectedItem(getPayload().getSubTipo());

        txtPuntosHistoria.setEditable(false);
        txtEsfuerzoCliente.setEditable(false);

        txtEsfuerzoCliente.setText(getPayload().getEsfuerzo());

        txtPuntosHistoria.setText(getPayload().getPuntosHistoria());


        txtHistoriaUsuario.setText(getPayload().getHistoriaUsuario());

        //txtEsfuerzoCliente.setText(getPayload().getEsfuerzoCliente());

        jtbBitacora.getModel().load(getPayload().getBitacora());
        List<FenixResponsable> responsablesEsfuerzos = jtbResponsables.getModel().getList();

        for (Actor actor : SettingsService.getInstance().getSettings().getActores()) {
            responsablesEsfuerzos.add(new FenixResponsable(null, actor.getNombre(), actor.getNumeroEmpleadoEveris(), Utils.EMPTY_STRING));
        }

        String[] responsables = getPayload().getResponsable() != null ? getPayload().getResponsable().split("-") : new String[0];
        String[] esfuerzos = getPayload().getEsfuerzo() != null ? getPayload().getEsfuerzo().split("-") : new String[0];
        String[] subtiposTareas = getPayload().getSubTipo() != null ? getPayload().getSubTipo().split("-") : new String[0];

        for (int i = 0; i < responsables.length; i++) {
            final int index = i;
            FenixResponsable fenixResponsable = responsablesEsfuerzos.stream().filter(r -> r.getNumero().equals(responsables[index])).findFirst().orElse(null);
            if (fenixResponsable != null) {
                fenixResponsable.setEsfuerzo(Double.parseDouble(esfuerzos[index]));
                fenixResponsable.setSubtipoTarea(subtiposTareas[index]);
            } else {
                Double esfuerzo = StringUtils.isNotEmpty(esfuerzos[index]) ? Double.parseDouble(esfuerzos[index]) : null;
                fenixResponsable = new FenixResponsable(esfuerzo, null, responsables[index], Utils.getSubtipo(subtiposTareas[index]));
                responsablesEsfuerzos.add(fenixResponsable);
            }
        }

      /*  if(subtipos.toString().equals("null") || subtipos.toString().equals("null-null")){
            throw new AppException("SUB-TIPOS no hay sido cumplido o datos invalidos");
        }else{
            getPayload().setSubTipo(subtipos.toString());
        }*/


    }

    @Override
    public JComponent createCentralPanel() {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new GridBagLayout());

        JLabel lblSummary = new JLabel(Internationalization.getStringTranslated("nameLower"));
        JLabel lblDescription = new JLabel();
        JLabel lblComments = new JLabel();
        JLabel lblCodigoPeticionCliente = new JLabel(Internationalization.getStringTranslated("codePetitionClientLower"));

        JLabel lblIdPeticion = new JLabel(Internationalization.getStringTranslated("idPetitionLower"));


        JLabel lblEstado = new JLabel(Internationalization.getStringTranslated("stat"));
        JLabel lblTipo = new JLabel(Internationalization.getStringTranslated("typeLower"));
        //JLabel lblSubtipo = new JLabel("Subtipo");
        JLabel lblPuntosHistoria = new JLabel(Internationalization.getStringTranslated("storyPointsLower"));
        JLabel lblEsfuerzo = new JLabel(Internationalization.getStringTranslated("clientEffort"));
        JLabel lblHistoriaUsuario = new JLabel(Internationalization.getStringTranslated("userStoryLower"));
        JLabel lblResponsables = new JLabel(Internationalization.getStringTranslated("responsibleLower"));
        JLabel lblFechaPrevistaProyecto = new JLabel("Fecha prevista");

        JButton addBitacoraBtn = new JButton(Internationalization.getStringTranslated("addComment"));
        addBitacoraBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtbBitacora.addRow(new Bitacora("-", ""));
            }
        });


        txtCodigoPeticionCliente = new JTextField();
        txtIdPeticion = new JTextField();

        cmbEstado = new JComboBox();
        cmbTipo = new JComboBox();
        // cmbSubTipo = new JComboBox();
        txtPuntosHistoria = new JTextField();
        txtHistoriaUsuario = new JTextField();
        txtEsfuerzoCliente = new JTextField();

        FenixResponsablesTableModel responsablesTableModel = new FenixResponsablesTableModel(new ArrayList<FenixResponsable>());
        jtbResponsables = new JenixTable(responsablesTableModel);
        responsablesScrollPane = new JScrollPane(jtbResponsables, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        responsablesScrollPane.setMinimumSize(new Dimension(200, 120));
        jtbResponsables.setFillsViewportHeight(true);

        responsablesTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                double puntos;
                double esfurzo;
                double arredondado;

                StringBuilder responsables = new StringBuilder();
                StringBuilder esfuerzos = new StringBuilder();
                StringBuilder subtipos = new StringBuilder();
                splitAttributes(responsables, esfuerzos, subtipos);

                txtEsfuerzoCliente.setText(Double.toString(FenixAcc.calculateTotalEsfuerzo(esfuerzos.toString())));

                //Add by vics
                // try{
                esfurzo = Double.valueOf(txtEsfuerzoCliente.getText());
                puntos = esfurzo * 0.375;

                arredondado = Math.round(puntos);

                txtPuntosHistoria.setText(String.valueOf(arredondado));
             /*   }catch(Exception ex){
                    throw new AppException("TESTE");
                }*/


            }
        });
        jtbResponsables.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JComboBox accSubTypeEditor = new JComboBox();
        SwingUtil.loadComboBox(AccSubType.class, accSubTypeEditor, true);

        jtbResponsables.getColumnModel().getColumn(FenixResponsablesTableModel.Columns.ESFUERZO.ordinal()).setCellEditor(new NumberCellEditor());
        jtbResponsables.getColumnModel().getColumn(FenixResponsablesTableModel.Columns.SUBTIPO_TAREA.ordinal()).setCellEditor(new DefaultCellEditor(accSubTypeEditor));

        BitacoraTableModel bitacoraTableModel = new BitacoraTableModel(new ArrayList<>());
        jtbBitacora = new JenixTable(bitacoraTableModel);
        bitacoraScrollPane = new JScrollPane(jtbBitacora, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bitacoraScrollPane.setMinimumSize(new Dimension(200, 120));
        jtbBitacora.setFillsViewportHeight(true);

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

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblComments, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(bitacoraScrollPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(addBitacoraBtn, constraints);

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


        /*AQUI*/
        constraints.gridx = 0;
        constraints.gridy = ++fila + 2;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblIdPeticion, constraints);
        constraints.weightx = 1;
        constraints.gridwidth = 1;


        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(txtCodigoPeticionCliente, constraints);


        /*AQUI*/
        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        panel.add(txtIdPeticion, constraints);

        /*AQUI*/
        constraints.gridx = 1;
        constraints.gridy = fila - 1;
        panel.add(txtHistoriaUsuario, constraints);


        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblEstado, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        panel.add(cmbEstado, constraints);

        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblTipo, constraints);
        constraints.weightx = 1;

     /*   constraints.gridx = 1;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblSubtipo, constraints);
        constraints.weightx = 1;
*/
        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        panel.add(cmbTipo, constraints);


        //constraints.gridx = 1;
        // constraints.gridy = fila;
        // panel.add(cmbSubTipo, constraints);

        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblPuntosHistoria, constraints);
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = fila + 1;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblEsfuerzo, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        panel.add(txtPuntosHistoria, constraints);

        constraints.gridx = 1;
        constraints.gridy = fila + 1;
        panel.add(txtEsfuerzoCliente, constraints);


        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblResponsables, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila + 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 5;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(responsablesScrollPane, constraints);


        return panel;
    }

    @Override
    public void fillPayLoad() {

        LocalVariables lv = new LocalVariables();
        //String idpeticion = lv.readOtInfoFile(WorkingJira.getIdOt());
        String idpeticion = lv.readOtInfoFile(WorkingJira.getIdOt()).getId_peticion();

        getPayload().setDescripcion(txaDescripcion.getText().substring(0,499));
        getPayload().setNombre(txtNombre.getText());
        getPayload().setIdPeticion(idpeticion);


        if (StringUtils.isBlank(txtCodigoPeticionCliente.getText())) {
            throw new AppException(Internationalization.getStringTranslated("appExeptionClientPetitionCodeNeed"));
        } else {
            getPayload().setCodigoPeticionCliente(txtCodigoPeticionCliente.getText());
        }

        if (txtIdPeticion.equals(null) || StringUtils.isBlank(txtIdPeticion.getText())) {
            throw new AppException(Internationalization.getStringTranslated("appExeptionPetitionCodeNeed"));
        } else {
            getPayload().setIdPeticion(txtIdPeticion.getText());

        }

        if(cmbEstado.getSelectedIndex() == -1 || StringUtils.isBlank(cmbEstado.getSelectedItem().toString())){
            throw new AppException("Estado es requerido");

        }else{
            getPayload().setEstado((String) cmbEstado.getSelectedItem());
        }

        getPayload().setTipo((String) cmbTipo.getSelectedItem());
        // getPayload().setSubTipo((String)cmbSubTipo.getSelectedItem());

        getPayload().setPuntosHistoria(txtPuntosHistoria.getText());

        getPayload().setHistoriaUsuario(txtHistoriaUsuario.getText());

        if (txtEsfuerzoCliente.getText().equals(null) || StringUtils.isBlank(txtEsfuerzoCliente.getText()) || txtEsfuerzoCliente.getText().equals("0.0")) { //validacao esfurzo
            throw new AppException(Internationalization.getStringTranslated("appExeptionEffortOrDataFailed"));
        } else {
            //getPayload().setEsfuerzoCliente(txtEsfuerzoCliente.getText());
            getPayload().setEsfuerzoCliente(txtEsfuerzoCliente.getText());
            //verif();
        }

          /*  if(txtEsfuerzoCliente.getText().equals("8")){
                txtPuntosHistoria.setText("3");
                getPayload().setPuntosHistoria(txtPuntosHistoria.getText());
            }*/

        getPayload().setBitacora(jtbBitacora.getList());

        StringBuilder responsables = new StringBuilder();
        StringBuilder esfuerzos = new StringBuilder();
        StringBuilder subtipos = new StringBuilder();
        splitAttributes(responsables, esfuerzos, subtipos);

        getPayload().setEsfuerzo(esfuerzos.toString());
        getPayload().setResponsable(responsables.toString());

        System.out.println("hehe " + subtipos.toString());

        //System.out.println(subtipos.length());


        if (StringUtils.isBlank(subtipos.toString()) || subtipos.toString().matches("^-(.*)|(.*)-$|(.*)--(.*)")) {
            throw new AppException(Internationalization.getStringTranslated("subTypeUper") +  Internationalization.getStringTranslated("appExeptionEmptyeOrInvalidData") ); //("^-(.*)|(.*)-$|(.*)--(.*)")
        } else {
            getPayload().setSubTipo(subtipos.toString());
        }


    }

    @Override
    public void initialize() {

    }

    private void splitAttributes(StringBuilder responsables, StringBuilder esfuerzos, StringBuilder subtipos) {
        List<FenixResponsable> fenixResponsables = jtbResponsables.getList();

        for (FenixResponsable fenixResponsable : fenixResponsables) {
            if (fenixResponsable.getEsfuerzo() == null) {
                continue;
            }
            if (responsables.length() > 0) {
                responsables.append("-");
                esfuerzos.append("-");
                subtipos.append("-");
            }
            responsables.append(fenixResponsable.getNumero());
            esfuerzos.append(fenixResponsable.getEsfuerzo());
            subtipos.append(fenixResponsable.getSubtipoTarea());
            System.out.println("esfuerzo " + fenixResponsable.getEsfuerzo());
        }
    }

    private void verif() {
        LocalVariables lv = new LocalVariables();
        OtInfo otinfo = lv.readOtInfoFile(WorkingJira.getIdOt());
        String codigopeticion = lv.readOtInfoFile(WorkingJira.getIdOt()).getCodigoPeticionCliente();
        if (!StringUtils.isBlank(codigopeticion)) {
            txtCodigoPeticionCliente.setText(codigopeticion);
            System.out.println("YA AQUI");
            updateCodigoPeticion(codigopeticion);

        } else {
            System.out.println("SIM ENTREI");
            txtCodigoPeticionCliente.setText(getPayload().getCodigoPeticionCliente());
            otinfo.setCodigoPeticionCliente(txtCodigoPeticionCliente.getText());
            lv.setValuesOtInfoFile(WorkingJira.getIdOt(), otinfo);
        }

    }

    private void updateCodigoPeticion(String codigo) {
        LocalVariables lv = new LocalVariables();
        OtInfo otinfo = lv.readOtInfoFile(WorkingJira.getIdOt());
        if (!txtCodigoPeticionCliente.getText().equals(codigo) && !StringUtils.isBlank(txtCodigoPeticionCliente.getText())) {
            System.out.println("Entrou");
            otinfo.setCodigoPeticionCliente(txtCodigoPeticionCliente.getText());
            lv.setValuesOtInfoFile(WorkingJira.getIdOt(), otinfo);
        }
    }

    private void updateCodigoPeticionCliente() {
        LocalVariables lv = new LocalVariables();
        OtInfo otinfo = lv.readOtInfoFile(WorkingJira.getIdOt());
        if (StringUtils.isBlank(txtIdPeticion.getText())) {
            lv.setValuesOtInfoFile(WorkingJira.getIdOt(), lv.readOtInfoFile(WorkingJira.getIdOt()));
        } else {
            otinfo.setId_peticion(txtIdPeticion.getText());
            lv.setValuesOtInfoFile(WorkingJira.getIdOt(), otinfo);
        }

    }

}
