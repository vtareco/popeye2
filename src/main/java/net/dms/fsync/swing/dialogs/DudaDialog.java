package net.dms.fsync.swing.dialogs;

import net.dms.fsync.swing.components.JenixDialog;
import net.dms.fsync.swing.components.MyColors;
import net.dms.fsync.synchronizer.fenix.entities.FenixDuda;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;


public class DudaDialog extends JenixDialog<FenixDuda> {
    private JTextArea txtDudaDescription;


    public DudaDialog(Component parent, FenixDuda initialPayload) {
        super(parent, initialPayload);
        // TODO FIXME, move to abstract class
        this.setSize(550, 350);
        setLocationRelativeTo(parent);
        setTitle("Creación de Duda");
        setModal(true);
        setResizable(false);
        setVisible(true);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void edit() {
        txtDudaDescription.setText(getPayload().getDescripcion());
    }

    @Override
    public JComponent createCentralPanel() {

        JPanel panelDuda = new JPanel();
        panelDuda.setBorder(BorderFactory.createEtchedBorder());
        panelDuda.setLayout(new GridBagLayout());


        JLabel lblDescription = new JLabel("Description");

        txtDudaDescription = new JTextArea();
        txtDudaDescription.setRows(10);
        txtDudaDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane sp = new JScrollPane(txtDudaDescription);
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

    /*    constraints.gridx = 0;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblSummary, constraints);
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        panel.add(txtSummary, constraints);*/

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panelDuda.add(lblDescription, constraints);
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        panelDuda.add(sp, constraints);

        return panelDuda;
    }

    @Override
    public void fillPayLoad() {
        // TODO FIXME, use a builder

        getPayload().setDescripcion(txtDudaDescription.getText());

       if(StringUtils.isBlank(txtDudaDescription.getText())){
           txtDudaDescription.setBackground(MyColors.TABLE_FIELD_REQUIRED);
       }

        //getPayload().setIdRequerimiento(Long.valueOf("1218336")); //PETICAO

        System.out.println("AGORA "+getPayload().getIdRequerimiento());
      /*
        duda.setDescripcion(txtDudaDescription.getText());
        duda.setEstado(DudaEstadoType.ABIERTA.getDescription());*/
       /* duda.setIdRelacionada(Long.valueOf("123"));
        duda.setIdRequerimiento(Long.valueOf("1234"));
        duda.setIdDuda(Long.valueOf("12345"));*/


  /* getPayload().setEstado(IncidenciaEstadoType.EN_ANALISIS.getDescription());
        getPayload().setImpacto(IncidenciaImpactoType.MEDIO.getDescription());
        getPayload().setResueltaPorCliente("NO");
        getPayload().setPrioridad(IncidenciaPrioridadType.MEDIA.getDescription());
        getPayload().setLocalizadaEn(IncidenciaLocalizadaEnType.PEER_REVIEW_CODIGO.getDescription());
        getPayload().setTipoIncidencia(IncidenciaTipoType.ERROR_EN_CODIGO.getDescription());
        getPayload().setEsfuerzoHh(1d);
        getPayload().setFechaInicio(new Date());
        getPayload().setUrgencia(IncidenciaUrgenciaType.MEDIA.getDescription());*/

    }


    @Override
    public void initialize() {

    }



}
