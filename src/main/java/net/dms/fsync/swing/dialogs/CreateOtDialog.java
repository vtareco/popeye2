package net.dms.fsync.swing.dialogs;

import com.sun.deploy.net.proxy.pac.PACFunctions;
import net.dms.fsync.swing.components.JNumberField;

import javax.swing.*;

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

   // Icon icon = UIManager.getIcon("OptionPane.informationIcon");

    public CreateOtDialog(JPanel panel) {
        setLayout(null);
        setSize(450,340);
        setTitle("Create new OT folder");
        setLocationRelativeTo(panel);
        setModal(true);
        setResizable(false);
        loadDialog();
    }

    public void loadDialog(){

        txtTitle=new JLabel();
        txtTitle.setText("Create new OT Folder");
        txtTitle.setBounds(150, 43, 152, 14);
        add(txtTitle);

        txtId=new JLabel();
        txtId.setText("ID_OT:");
        txtId.setBounds(56, 108, 45, 14);
        add(txtId);

        fieldId=new JNumberField();
        fieldId.setBounds(141, 105, 165, 20);
        add(fieldId);

        txtDescription=new JLabel();
        txtDescription.setText("Descripcion:");
        txtDescription.setBounds(56, 163, 72, 14);
        add(txtDescription);

        txtOptional=new JLabel();
        txtOptional.setText("Optional");
        txtOptional.setBounds(56, 179, 56, 14);
        txtOptional.setSize(10,0);
        add(txtOptional);

        fieldDescription=new JTextField();
        fieldDescription.setBounds(141, 160, 165, 20);
        add(fieldDescription);

       /* txtIdPeticion=new JLabel();
        txtIdPeticion.setText("ID_Peticion:");
        txtIdPeticion.setBounds(50, 130, 62, 14);
        add(txtIdPeticion);*/

     /*   fieldIdPetcion=new JNumberField();
        fieldIdPetcion.setBounds(141, 177, 172, 20);
        add(fieldIdPetcion);*/

        btnCreate=new JButton();
        btnCreate.setText("Create");
        btnCreate.setBounds(284, 227, 76, 23);
        add(btnCreate);

        btnCancel=new JButton();
        btnCancel.setBounds(75, 227, 76, 23);
        btnCancel.setText("Cancel");
        add(btnCancel);

    }
}
