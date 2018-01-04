package net.dms.popeye.handlers.jfsynchronizer.swing.dialogs;

import net.dms.popeye.handlers.jfsynchronizer.bussiness.FenixService;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixIncidencia;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.*;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.JenixDialog;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Date;


public class InternalIncidenceDialog extends JenixDialog<FenixIncidencia> {
    private JTextField txtSummary;
    private JTextArea txaDescription;



    public InternalIncidenceDialog(Component parent, FenixIncidencia initialPayload) {
        super(parent, initialPayload);
        // TODO FIXME, move to abstract class
        this.setSize(550, 350);
        setLocationRelativeTo(parent);
        setTitle("Internal incidence");
        setModal(true);
        setResizable(false);
        setVisible(true);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void edit() {
        txtSummary.setText(getPayload().getNombreIncidencia());
        txaDescription.setText(getPayload().getDescripcion());
    }

    @Override
    public JComponent createCentralPanel() {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new GridBagLayout());

        JLabel lblSummary = new JLabel("Summary");
        JLabel lblDescription = new JLabel("Description");

        txtSummary = new JTextField(15);
        txaDescription = new JTextArea();
        txaDescription.setRows(10);
        txaDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane sp = new JScrollPane(txaDescription);
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
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        panel.add(txtSummary, constraints);

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblDescription, constraints);
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        panel.add(sp, constraints);

        return panel;
    }

    @Override
    public void fillPayLoad() {
        // TODO FIXME, use a builder
        getPayload().setDescripcion(txaDescription.getText());
        getPayload().setNombreIncidencia(txtSummary.getText());
        getPayload().setEstado(IncidenciaEstadoType.EN_ANALISIS.getDescription());
        getPayload().setImpacto(IncidenciaImpactoType.MEDIO.getDescription());
        getPayload().setResueltaPorCliente("NO");
        getPayload().setPrioridad(IncidenciaPrioridadType.MEDIA.getDescription());
        getPayload().setLocalizadaEn(IncidenciaLocalizadaEnType.PEER_REVIEW_CODIGO.getDescription());
        getPayload().setTipoIncidencia(IncidenciaTipoType.ERROR_EN_CODIGO.getDescription());
        getPayload().setEsfuerzoHh(1d);
        getPayload().setFechaInicio(new Date());
        getPayload().setUrgencia(IncidenciaUrgenciaType.MEDIA.getDescription());

    }

    @Override
    public void initialize() {

    }



}
