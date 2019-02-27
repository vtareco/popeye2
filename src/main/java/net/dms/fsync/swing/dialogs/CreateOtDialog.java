package net.dms.fsync.swing.dialogs;

import com.sun.deploy.net.proxy.pac.PACFunctions;

import javax.swing.*;

public class CreateOtDialog extends JDialog {

    private JLabel txtTitle;
    private JLabel txtId;
    private JLabel txtDescription;
    private JTextField fieldId;
    private JTextField fieldDescription;
    private JButton cancelAction;
    private JButton createAction;


   // Icon icon = UIManager.getIcon("OptionPane.informationIcon");

    public CreateOtDialog(JPanel panel) {
        setLayout(null);
        setSize(450,300);
        setTitle("Create new OT folder");
        setLocationRelativeTo(panel);
        setModal(true);
        setResizable(false);
        setUndecorated(true);
        loadDialog();
    }

    public void loadDialog(){

        txtTitle=new JLabel();
        txtTitle.setText("Create new OT Folder");
        txtTitle.setBounds(150, 43, 152, 14);



    }
}
