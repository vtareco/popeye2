package net.dms.fsync.swing.Panes;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.Internationalization;
import net.dms.fsync.settings.entities.Application;
import net.dms.fsync.swing.components.Toast;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import net.dms.fsync.synchronizer.fenix.business.FenixService;
import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.java2d.opengl.WGLSurfaceData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaCorrecao extends JPanel {

    private JLabel txtOtCorrectora;
    public JComboBox comboOtCorrectora;
    private JLabel txtAccCorrectora;
    public JComboBox comboAccCorrectora;
    private JLabel txtTareaCausante;
    public JTextField fieldTareaCausante;
    private JLabel txtDescripcion;
    public JTextArea fieldArea;
    private JScrollPane scrollPane;
    FenixService fenixService;

    public IncidenciaCorrecao(String idAcc, List<FenixAcc> accTable) {
        setLayout(null);
        //fenixService = new FenixService();
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        fenixService = context.getBean(FenixService.class);
        loadPanel(idAcc,accTable);
    }

    public void loadPanel(String idAcc,List<FenixAcc> accTable){

        textLabels();

        comboBoxes(accTable);

        fieldsComponents(idAcc);

        fieldArea = new JTextArea();
        fieldArea.setText(null);
        fieldArea.setLineWrap(true);

        scrollerConfiguration();


    }

    private void textLabels() {
        txtOtCorrectora = new JLabel();
        txtOtCorrectora.setBounds(33, 28, 90, 14);
        txtOtCorrectora.setText("OT Correctora*");
        add(txtOtCorrectora);


        txtAccCorrectora = new JLabel();
        txtAccCorrectora.setBounds(202, 28, 100, 14);
        txtAccCorrectora.setText("ACC Correctora*");
        add(txtAccCorrectora);

        txtTareaCausante = new JLabel();
        txtTareaCausante.setBounds(357, 28, 100, 14);
        txtTareaCausante.setText("Tarea Causante*");
        add(txtTareaCausante);


        txtDescripcion = new JLabel();
        txtDescripcion.setBounds(33, 97, 76, 14);
        txtDescripcion.setText("Description*");
        add(txtDescripcion);

    }

    private void comboBoxes(List<FenixAcc> accTable) {
        comboOtCorrectora = new JComboBox();
        comboOtCorrectora.setBounds(33, 53, 89, 20);
        add(comboOtCorrectora);

        comboAccCorrectora = new JComboBox();
        comboAccCorrectora.setBounds(202, 53, 89, 20);
        add(comboAccCorrectora);


        ArrayList<String> idOts = allAction(accTable);

        comboOtCorrectora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("YAAA CLIQUEI");
                refreshComboBox(idOts);
                allAction(getACCsFromSelectedOT());
            }
        });
    }

    private List<FenixAcc> getACCsFromSelectedOT() {
        List<FenixAcc> accList = new ArrayList<>();
      try{

          for (FenixAcc acc : fenixService.searchAccByPeticionId(Long.valueOf(WorkingJira.getIdOt().substring(0, 7)), false)) {
              System.out.println("EXCEL VALUES "+acc.getIdAcc()+" "+acc.getHistoriaUsuario());
              accList.add(acc) ;
          }

      }catch (AppException e){
          e.printStackTrace();
      }

        return accList;
    }

    private ArrayList<String> allAction(List<FenixAcc> accTable) {
        ArrayList<String> idOts = new ArrayList<>();
        String projectPath = getProjectPath();
        File folderLocation = new File(projectPath);
        for (final File files: folderLocation.listFiles()){
            if(files.isDirectory()){
                if(files.getName().length() >= 8){
                    if(files.getName().substring(0,7).matches("^[0-9]*$")) { //.matches("^(\\d)*")
                        System.out.println("Files bons "+files.getName().substring(0,7));
                        String idOt = files.getName().substring(0,7);
                        comboOtCorrectora.addItem(idOt);
                        idOts.add(idOt); /*PARA SAIR*/
                        if(idOt.equals(WorkingJira.getIdOt().substring(0,7))) { /* PROBLEMA AQUI, NA TABELA ACC NAO TENHO ID ACC*/
                            for (FenixAcc acc : getACCsFromSelectedOT()) {
                                comboOtCorrectora.setSelectedItem(idOt);
                                comboAccCorrectora.addItem(acc.getIdAcc());
                            }
                        }


                    }
                }
            }

        }
        return idOts;
    }

    private void fieldsComponents(String idAcc) {
        fieldTareaCausante = new JTextField();
        fieldTareaCausante.setEditable(false);
        fieldTareaCausante.setText(idAcc);
        //fieldTareaCausante.setText(WorkingJira.getIdAcc());
        fieldTareaCausante.setBounds(357, 53, 89, 20);
        add(fieldTareaCausante);
    }

    private void scrollerConfiguration() {
        scrollPane = new JScrollPane(fieldArea);
        scrollPane.setBounds(33, 122, 413, 110);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

    }

    public void refreshComboBox(List<String> idOts) {
        if(comboAccCorrectora.getItemCount() == 0 || comboAccCorrectora.getModel() == null){
            Toast.display("No values found, check Excel file", Toast.ToastType.ERROR);
        }
        String fullIdOt ="";
        String idOtSelected =  comboOtCorrectora.getSelectedItem().toString();
        WorkingJira.setIdOt(idOtSelected);
        String projectPath = getProjectPath();
        File folderLocation = new File(projectPath);
        for (final File files: folderLocation.listFiles()) {
            if (files.isDirectory()) {
                if(files.getName().contains(WorkingJira.getIdOt())){
                   fullIdOt = files.getName();
                }
            }
        }

        WorkingJira.setIdOt(fullIdOt);
        WorkingJira.getIdOt();
        comboOtCorrectora.removeAllItems();
        comboAccCorrectora.removeAllItems();
       /* List<String> otFiles = idOts ;
        SwingUtil.loadComboBox(otFiles, comboOtCorrectora, true);*/

    }

    private String getProjectPath() {
        LocalVariables lv = new LocalVariables();
        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        return ap.getWorkingDirectory();
    }


}
