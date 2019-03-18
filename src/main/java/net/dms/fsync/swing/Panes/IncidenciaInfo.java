package net.dms.fsync.swing.Panes;

import net.dms.fsync.synchronizer.fenix.entities.enumerations.IncidenciaLocalizadaEnType;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.IncidenciaRowType;

import javax.swing.*;

public class IncidenciaInfo extends JPanel {

    private JLabel txtNome;
    public JTextField fieldNome;
    private JLabel txtTipoIncidencia;
    public JComboBox comboTipoIncidencia;
    private JLabel txtLocalizadaEm;
    public JComboBox comboLocalizadaEm;
    private JLabel txtUrgencia;
    public JComboBox comboUrgencia;
    private JLabel txtImpacto;
    public JComboBox comboImpacto;
    private JLabel txtPrioridade;
    public JComboBox comboPrioridade;



    public IncidenciaInfo() {
        setLayout(null);
        loadPane();
    }

    public void loadPane(){

        txtNome = new JLabel();
        txtNome.setBounds(39, 19, 46, 14);
        txtNome.setText("Nome*");
        this.add(txtNome);

        fieldNome = new JTextField();
        fieldNome.setBounds(39, 44, 407, 20);
        this.add(fieldNome);


        labelsComponents();

        comboBoxes();

       /* area = new JTextArea();
        area.setLineWrap(true);
        area.setText(null);*/


    }

    private void labelsComponents() {

        txtTipoIncidencia = new JLabel();
        txtTipoIncidencia.setBounds(39, 90, 100, 14);
        txtTipoIncidencia.setText("Tipo Incidência");
        this.add(txtTipoIncidencia);


        txtLocalizadaEm = new JLabel();
        txtLocalizadaEm.setBounds(290, 90, 90, 14);
        txtLocalizadaEm.setText("Localizada Em*");
        this.add(txtLocalizadaEm);


        txtUrgencia = new JLabel();
        txtUrgencia.setBounds(39, 166, 60, 14);
        txtUrgencia.setText("Urgência*");
        this.add(txtUrgencia);


        txtImpacto = new JLabel();
        txtImpacto.setBounds(187, 166, 60, 14);
        txtImpacto.setText("Impacto*");
        this.add(txtImpacto);


        txtPrioridade = new JLabel();
        txtPrioridade.setBounds(346, 166, 75, 14);
        txtPrioridade.setText("Prioridade*");
        this.add(txtPrioridade);
    }

    private void comboBoxes() {
        comboTipoIncidencia = new JComboBox();
        comboTipoIncidencia.setBounds(39, 115, 156, 20);
        comboTipoIncidencia.addItem(" ");
        comboTipoIncidencia.addItem("ERROR_DF");
        comboTipoIncidencia.addItem("ERROR_DT");
        comboTipoIncidencia.addItem("ERROR_EJECUCION_PRUEBAS");
        comboTipoIncidencia.addItem("ERROR_EN_CODIGO");
        comboTipoIncidencia.addItem("ERROR_PLAN_DE_PRUEBAS");
        comboTipoIncidencia.addItem("ERROR_ESTANIDARES_CODIFICACION");
        this.add(comboTipoIncidencia);

        comboLocalizadaEm = new JComboBox();
        comboLocalizadaEm.setBounds(290, 115, 156, 20);
        comboLocalizadaEm.addItem(" ");
        comboLocalizadaEm.addItem(IncidenciaLocalizadaEnType.HERRAMIENTA_TESTING.toString());
        comboLocalizadaEm.addItem("PEER_REVIEW_CODIGO");
        comboLocalizadaEm.addItem("PEER_REVIEW_DF");
        comboLocalizadaEm.addItem("PEER_REVIEW_EJECUCION_PRUEBAS");
        comboLocalizadaEm.addItem("PRUEBAS_FUNCIONALIDAD");
        comboLocalizadaEm.addItem("PRUEBAS_INTEGRADAS");
        comboLocalizadaEm.addItem("PRUEBAS_UNITARIAS");
        this.add(comboLocalizadaEm);

        comboUrgencia = new JComboBox();
        comboUrgencia.setBounds(39, 191, 103, 20);
        comboUrgencia.addItem(" ");
        comboUrgencia.addItem("ALTA");
        comboUrgencia.addItem("MEDIA");
        comboUrgencia.addItem("BAJA");
        this.add(comboUrgencia);


        comboImpacto = new JComboBox();
        comboImpacto.setBounds(187, 191, 103, 20);
        comboImpacto.addItem(" ");
        comboImpacto.addItem("BLOQUEANTE");
        comboImpacto.addItem("MEDIO");
        comboImpacto.addItem("BAJO");
        this.add(comboImpacto);


        comboPrioridade = new JComboBox();
        comboPrioridade.setBounds(343, 191, 103, 20);
        comboPrioridade.addItem(" ");
        comboPrioridade.addItem("CRITICA");
        comboPrioridade.addItem("ALTA");
        comboPrioridade.addItem("MEDIA");
        comboPrioridade.addItem("BAJA");
        comboPrioridade.addItem("MUY BAJA");
        this.add(comboPrioridade);
    }


}
