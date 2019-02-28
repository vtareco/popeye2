package net.dms.fsync.swing.dialogs;

import com.sun.deploy.net.proxy.pac.PACFunctions;
import net.dms.fsync.swing.components.JNumberField;

import javax.swing.*;
import java.awt.*;

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
        txtOptional.setText("Optional");
        txtOptional.setBounds(50, 200, 56, 14);
        txtOptional.setFont(new Font("Tahoma", Font.PLAIN, 10));
        add(txtOptional);


        fieldDescription=new JNumberField();
        fieldDescription.setBounds(141, 187, 172, 20);
        add(fieldDescription);

        txtIdPeticion=new JLabel();
        txtIdPeticion.setText("ID_Peticion:");
        txtIdPeticion.setBounds(50, 140, 72, 14);
        add(txtIdPeticion);


        fieldIdPetcion=new JTextField();
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

    }
}
