package net.dms.fsync.swing.dialogs;

import net.dms.fsync.swing.Panes.AddFilter;
import net.dms.fsync.swing.Panes.ServerChangePane;
import net.dms.fsync.swing.Panes.UserChangePane;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.JenixSettings;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog  extends JDialog {

    JButton save;

    public SettingsDialog(JTabbedPane tabbedPane, JenixSettings js){
        setLayout(null);
        this.setSize(600, 400);
        setTitle("Jenix Settings");
        setLocationRelativeTo(tabbedPane);
        setModal(true);
        setResizable(false);

        loadDialog(js);
    }


    private void loadDialog(JenixSettings js){

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setBounds(0, 0, 597, 399);
        getContentPane().add(tabbedPane);


        JPanel userChange = new UserChangePane(js);
        tabbedPane.addTab("User Configuration", null, userChange, null);

        JPanel serverChange = new ServerChangePane(js);
        tabbedPane.addTab("Application Properties", null, serverChange, null);

        JPanel addFilter = new AddFilter(js);
        tabbedPane.addTab("Filters Configuration", null, addFilter, null);



        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);


        setVisible(true);
    }

}
