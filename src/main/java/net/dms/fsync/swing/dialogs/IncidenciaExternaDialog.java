package net.dms.fsync.swing.dialogs;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.entities.Application;
import net.dms.fsync.swing.Panes.IncidenciaCorrecao;
import net.dms.fsync.swing.Panes.IncidenciaInfo;
import net.dms.fsync.swing.Panes.IncidenciasDatas;
import net.dms.fsync.swing.components.JenixTable;
import net.dms.fsync.swing.components.Toast;
import net.dms.fsync.swing.models.IncidenciaTableModel;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import net.dms.fsync.synchronizer.fenix.business.FenixService;
import net.dms.fsync.synchronizer.fenix.entities.FenixIncidencia;
import net.dms.fsync.synchronizer.fenix.entities.IncidenciaExterna;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class IncidenciaExternaDialog extends JDialog {

    IncidenciaInfo incidenciaInfo = new IncidenciaInfo();
    IncidenciasDatas incidenciasDatas = new IncidenciasDatas();
    FenixService fenixService;
    private JButton cancelAction;
    private JButton createAction;
    private JenixTable<IncidenciaTableModel, FenixIncidencia> incidenciaTable;

    public IncidenciaExternaDialog(IncidenciaExterna incidencia/*JTabbedPane pane*/) {
        setLayout(null);
        this.setSize(497, 366);
        setTitle("Incidencias Externas");
        //setLocationRelativeTo(pane);
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        fenixService = context.getBean(FenixService.class);
        IncidenciaCorrecao incidenciaCorrecao = new IncidenciaCorrecao(incidencia.getIdAcc(), incidencia.getValuesAccTable());
        loadDialog(incidenciaCorrecao, incidencia.getValuesIncidenciaTable());
    }

    public void loadDialog(IncidenciaCorrecao incidenciaCorrecao, List<FenixIncidencia> excelIncidencias) {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 481, 277);
        getContentPane().add(tabbedPane);


        JPanel paneInfo = incidenciaInfo;
        tabbedPane.addTab("Info", null, paneInfo, null);

        JPanel panelCorrecao = incidenciaCorrecao;
        tabbedPane.addTab("Correção", null, panelCorrecao, null);

        JPanel panelDatas = incidenciasDatas;
        tabbedPane.addTab("Datas", null, panelDatas, null);

        cancelAction = new JButton();
        cancelAction.setBounds(36, 298, 89, 23);
        cancelAction.setText("Cancel");
        this.add(cancelAction);

        cancelAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        createAction = new JButton();
        createAction.setBounds(348, 298, 89, 23);
        createAction.setText("Create");
        this.add(createAction);
        createAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // IncidenciaExterna incidenciaExterna = new IncidenciaExterna();
                //IncidenciaTableModel incidenciaTableModel = incidenciaTable.getModel();
                try {
                    FenixIncidencia incidenciaExterna = new FenixIncidencia();
                    //incidenciaExterna.setIdAcc(incidenciaExterna.getIdAcc());

                    if(!fieldsNotFill(incidenciaCorrecao)){
                        incidenciaExterna.setNombreIncidencia(incidenciaInfo.fieldNome.getText());
                        incidenciaExterna.setTipoIncidencia(incidenciaInfo.comboTipoIncidencia.getSelectedItem().toString());
                        incidenciaExterna.setLocalizadaEn(incidenciaInfo.comboLocalizadaEm.getSelectedItem().toString());
                        incidenciaExterna.setUrgencia(incidenciaInfo.comboUrgencia.getSelectedItem().toString());
                        incidenciaExterna.setImpacto(incidenciaInfo.comboImpacto.getSelectedItem().toString());
                        incidenciaExterna.setPrioridad(incidenciaInfo.comboPrioridade.getSelectedItem().toString());

                        incidenciaExterna.setOtCorrector(incidenciaCorrecao.comboOtCorrectora.getSelectedItem().toString());
                        incidenciaExterna.setAccCorrector(incidenciaCorrecao.comboAccCorrectora.getSelectedItem().toString());
                        incidenciaExterna.setTareaCausante(incidenciaCorrecao.fieldTareaCausante.getText());
                        incidenciaExterna.setDescripcion(incidenciaCorrecao.fieldArea.getText());

                        incidenciaExterna.setFechaInicio((Date) incidenciasDatas.dataInicio.getModel().getValue());
                        incidenciaExterna.setFechaFin((Date) incidenciasDatas.dataFim.getModel().getValue());
                        incidenciaExterna.setFechaPrevistaCentro((Date) incidenciasDatas.dataCentro.getModel().getValue());


                        incidenciaExterna.setResueltaPorCliente("Si");
                        incidenciaExterna.setIdPeticionOt(WorkingJira.getIdOt().substring(0, 7));

                        excelIncidencias.add(incidenciaExterna);
                        fenixService.saveIncidencia(excelIncidencias);
                        //fenixService.saveIncidencia(Collections.singletonList(incidenciaExterna));
                        Toast.display("External Incidence was been added successfully ! ", Toast.ToastType.INFO);
                        dispose();
                    }


              /*  incidenciaExterna.setIdInterno(Long.valueOf("123"));
                incidenciaExterna.setIdPeticionOt("2312313");
                incidenciaExterna.setEsfuerzoHh(1d);

                incidenciaExterna.setEstado("BOM");
                incidenciaExterna.setIdIncidencia(Long.valueOf("5555"));
                incidenciaExterna.setPrioridad("BAIXA");*/


                    //accTable.addRow(copy);
                    //incidenciaTable.getModel().fireTableDataChanged();

                    //incidenciaTable.addRow((FenixIncidencia) incidencias);

                  /*  IncidenciaTableModel incidenciaTableModel = new IncidenciaTableModel(new ArrayList<>());
                    incidenciaTable = new JenixTable(incidenciaTableModel);

               incidenciaTable.addRow(incidenciaExterna);
               incidenciaTable.setDefaultRenderer(Object.class, new IncidenciaTableCellRenderer());*/


                } catch (AppException ee) {
                    ee.printStackTrace();
                }

            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setVisible(true);

    }

    public Boolean fieldsNotFill(IncidenciaCorrecao incidenciaCorrecao) {
        boolean check;

        if(StringUtils.isBlank(incidenciaInfo.fieldNome.getText()) || StringUtils.isBlank(incidenciaInfo.comboTipoIncidencia.getSelectedItem().toString()) ||
                StringUtils.isBlank(incidenciaInfo.comboLocalizadaEm.getSelectedItem().toString()) || StringUtils.isBlank(incidenciaInfo.comboUrgencia.getSelectedItem().toString()) ||
                StringUtils.isBlank(incidenciaInfo.comboImpacto.getSelectedItem().toString()) || StringUtils.isBlank(incidenciaInfo.comboPrioridade.getSelectedItem().toString())){

            Toast.display("Field not fill in Info Pane", Toast.ToastType.ERROR);

            check = true;

        }else if(StringUtils.isBlank(incidenciaCorrecao.comboOtCorrectora.getSelectedItem().toString()) || StringUtils.isBlank(incidenciaCorrecao.comboAccCorrectora.getSelectedItem().toString()) ||
                StringUtils.isBlank(incidenciaCorrecao.fieldTareaCausante.getText()) || StringUtils.isBlank(incidenciaCorrecao.fieldArea.getText())){

            Toast.display("Field not fill in Correção Pane", Toast.ToastType.ERROR);

            check = true;
        }else if(incidenciasDatas.dataInicio.getModel().getValue() == null || incidenciasDatas.dataFim.getModel().getValue() == null || incidenciasDatas.dataCentro.getModel().getValue() == null){
            Toast.display("Field not fill in Datas Pane", Toast.ToastType.ERROR);
            check = true;
        }else{
            check = false;
        }

            return check;
    }
}
