package net.dms.fsync.swing.dialogs;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.swing.components.JNumberField;
import net.dms.fsync.swing.components.Toast;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.OtInfo;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateOtDialog extends JDialog {

    private JLabel txtTitle;
    private JLabel txtId;
    private JLabel txtDescription;
    private JLabel txtOptional;
    private JLabel txtIdPeticion;
    private JTextField fieldId;
    private JTextField fieldDescription;
    private JTextField fieldIdPetcion;
    private JButton btnCancel;
    private JButton btnCreate;

    //Icon icon = UIManager.getIcon("OptionPane.");


    public CreateOtDialog(JPanel panel,JComboBox peticions) {
        setLayout(null);
        setSize(450,340);
        setTitle("Create new OT folder");
        setLocationRelativeTo(panel);
        setModal(true);
        setResizable(false);
        loadDialog(peticions);
    }

    public void loadDialog(JComboBox peticions){

        txtTitle=new JLabel();
        txtTitle.setText("Create new OT Folder");
        txtTitle.setBounds(141, 28, 152, 14);
        txtTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(txtTitle);

        txtId=new JLabel();
        txtId.setText("ID_OT:");
        txtId.setBounds(59, 93, 38, 14);
        add(txtId);

        fieldId=new JNumberField();
        fieldId.setBounds(141, 90, 172, 20);
        add(fieldId);

        txtDescription=new JLabel();
        txtDescription.setText("Descripcion:");
        txtDescription.setBounds(50, 186, 72, 14);
        add(txtDescription);

        txtOptional=new JLabel();
        txtOptional.setText("(Optional)");
        txtOptional.setBounds(50, 200, 56, 14);
        txtOptional.setFont(new Font("Tahoma", Font.PLAIN, 10));
        add(txtOptional);


        fieldDescription=new JTextField();
        fieldDescription.setBounds(141, 187, 172, 20);
        add(fieldDescription);

        txtIdPeticion=new JLabel();
        txtIdPeticion.setText("ID_Peticion:");
        txtIdPeticion.setBounds(50, 140, 72, 14);
        add(txtIdPeticion);


        fieldIdPetcion=new JNumberField();
        fieldIdPetcion.setBounds(141, 137, 172, 20);
        add(fieldIdPetcion);

        btnCreate=new JButton();
        btnCreate.setText("Create");
        btnCreate.setBounds(284, 257, 76, 23);
        add(btnCreate);

        btnCancel=new JButton();
        btnCancel.setBounds(75, 257, 76, 23);
        btnCancel.setText("Cancel");
        add(btnCancel);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOtFolder(peticions);
            }
        });

    }


    private void createOtFolder(JComboBox peticions){
        LocalVariables lv = new LocalVariables();
        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        String projectPath = ap.getWorkingDirectory();
        //int cont=0;
        //File file = null;
    try {

      /*  ArrayList<String> list = new ArrayList<>();
        if (peticions!= null){
            System.out.println(peticions.getItemCount());
            for(int i=0; i<peticions.getItemCount(); i++){
                list.add(String.valueOf(peticions.getItemAt(i)));
                System.out.println("porra "+peticions.getItemAt(i));

            }
            System.out.println("YOO "+list);
        }
        for (String peticiones : list) {
            if(peticiones.compareToIgnoreCase(nameOfCreatedFolder)!=0) {
                cont = 1;
            }else {
                cont = 0;
                break;
            }

        }*/
        String nameOfCreatedFolder = fieldId.getText()+ "-" + fieldDescription.getText();
        System.out.println("NOME "+nameOfCreatedFolder);
        if(StringUtils.isBlank(fieldId.getText()) || StringUtils.isBlank(fieldIdPetcion.getText()) || nameOfCreatedFolder.equals("-")){
            Toast.display("Please fill the fields", Toast.ToastType.ERROR);
            return;
        }
        if(!Files.exists(Paths.get(nameOfCreatedFolder))){
            OtInfo otinfo = new OtInfo();
            new File(nameOfCreatedFolder).mkdirs();
            otinfo.setId_peticion(fieldIdPetcion.getText());
            //otinfo.setCodigoPeticionCliente(fieldDescription.getText());
            lv.setValuesOtInfoFile(nameOfCreatedFolder,otinfo);
            Toast.display("Folder OT created Successfully", Toast.ToastType.INFO);
        }else{
            Toast.display("Folder OT not created, folder already exists", Toast.ToastType.ERROR);
        }

      /*  if(cont == 1){

            //refreshPeticionesDisponiblesCMB();
        }else if(cont == 0){

        }*/

    }catch (AppException e){
        e.printStackTrace();
    }


    }

}
