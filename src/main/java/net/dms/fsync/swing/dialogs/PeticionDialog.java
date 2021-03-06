package net.dms.fsync.swing.dialogs;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.swing.components.JNumberField;
import net.dms.fsync.swing.components.JTextFieldLimit;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.OtInfo;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class PeticionDialog extends JDialog{

   // private int value= 10000;
    //private NumberFormat peticionFormat;
    //private JFormattedTextField  txtIdPeticion;

    public JNumberField txtIdPeticion;
    private JLabel lblTitle;
    private JLabel lblId;
    private JButton btnSave;
    private JButton btnCancel;
    private JLabel topIcon;
    private String peticion;
    boolean check;
    Icon icon = UIManager.getIcon("OptionPane.informationIcon");
    // Image image = Toolkit.getDefaultToolkit().getImage("OptionPane.informationIcon");

    public PeticionDialog(JPanel panel,JComboBox peticionSelected) {
        setLayout(null);
        this.setSize(450, 300);
        setTitle("ID Peticion");
        // setIconImage(image);
        setLocationRelativeTo(panel);
        setModal(true);
        setResizable(false);
        setUndecorated(true);
        loadDialog(peticionSelected);
    }

    public void loadDialog(JComboBox peticionSelected) {

     /*   MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("*******");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter.setValidCharacters("0123456789");*/

        peticion=peticionSelected.getSelectedItem().toString();
        LocalVariables lv = new LocalVariables();
        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        String projectPath = ap.getWorkingDirectory();

        topIcon = new JLabel(icon);
        topIcon.setBounds(10, 11, 43, 43);
        this.add(topIcon);


        lblTitle = new JLabel();
        lblTitle.setText("ID Peticion");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblTitle.setBounds(177, 88, 87, 14);
        this.add(lblTitle);

        lblId = new JLabel();
        lblId.setText("ID:");
        lblId.setBounds(81, 138, 21, 14);
        this.add(lblId);

        txtIdPeticion = new JNumberField(); //new JFormattedTextField(peticionFormat);
       // txtIdPeticion.
        txtIdPeticion.setBounds(112, 135, 225, 20);
        txtIdPeticion.setColumns(10);
       // txtIdPeticion.addPropertyChangeListener("value", this);
        txtIdPeticion.setDocument(new JTextFieldLimit(7));
        this.add(txtIdPeticion);


        btnSave = new JButton();
        btnSave.setBounds(52, 210, 89, 23);
        btnSave.setText("Save");
        this.add(btnSave);

        btnCancel = new JButton();
        btnCancel.setBounds(289, 210, 89, 23);
        btnCancel.setText("Cancel");
        this.add(btnCancel);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selecionado: "+peticion);
                verification();
                if(!check){//se tiver preenchido
                    createFile(projectPath,peticion);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Petici�n Requerida !");
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                peticionSelected.setSelectedIndex(0);
            }
        });

      /*  addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("fechei");
                verification();


            }
        });*/

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }


    /*private void saveAction(String peticionSelected) {
        LocalVariables lv = new LocalVariables();
        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        String projectPath = ap.getWorkingDirectory();
        File file = new File(projectPath + "/" + peticionSelected + "/OT_INFO");
        try {
            if (!file.exists()) {
                new File(projectPath + "/" + peticionSelected + "/OT_INFO").mkdirs();
                createFile(projectPath, peticionSelected);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

    private void createFile(String projectPath, String peticionSelected) {
        LocalVariables lv = new LocalVariables();
        OtInfo otInfo = new OtInfo();
        otInfo.setId_peticion(txtIdPeticion.getText());

        lv.setValuesOtInfoFile(peticionSelected,otInfo);
     /*   try {
             //comentar PrintWriter writer = new PrintWriter(projectPath+"/"+peticionSelected+"/OT_INFO"+"/info.txt", "UTF-8");
               writer.println(txtIdPeticion.getText());
               writer.close(); //comentar
            new File(projectPath + "/" + peticionSelected + "/OT_INFO").mkdirs();

            JSONObject object = new JSONObject();
            object.put("ID_Peticion", txtIdPeticion.getText());

            try (FileWriter f = new FileWriter(projectPath + "/" + peticionSelected + "/OT_INFO" + "/info.json")) {
                f.write(object.toJSONString());
                System.out.println("OT_INFO: " + object);
            }

                  //comentar try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(projectPath+"/OT_INFO/info.txt"), "utf-8"))) {
                    writer.write(txtIdPeticion.getText());
                //comentar

        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    private void verification() {
        if(StringUtils.isBlank(txtIdPeticion.getText())){
           check=true;
        }else{
            check=false;
        }
    }

    /*@Override
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == txtIdPeticion) {
            value = ((Number)txtIdPeticion.getValue()).intValue();
        }
    }*/
}
