package net.dms.fsync.swing.dialogs;

import net.dms.fsync.swing.Panes.FilterManagePane;
import net.dms.fsync.swing.Panes.ServerChangePane;
import net.dms.fsync.swing.Panes.UserChangePane;
import net.dms.fsync.synchronizer.LocalVariables.entities.JsonPaths;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog  extends JDialog {

    JButton save;
/*
    public SettingsDialog(JTabbedPane tabbedPane, JenixSettings js){
        setLayout(null);
        this.setSize(600, 400);
        setTitle("Jenix Settings");
        setLocationRelativeTo(tabbedPane);
        setModal(true);
        setResizable(false);

        loadDialog(js);
    }
*/
    public SettingsDialog(JTabbedPane tabbedPane, JsonPaths jsonPaths){
        setLayout(null);
        this.setSize(600, 400);
        setTitle("Jenix Settings");
        setLocationRelativeTo(tabbedPane);
        setModal(true);
        setResizable(false);

        loadDialog(jsonPaths);
    }

    private void loadDialog(JsonPaths jsonPaths){

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setBounds(0, 0, 597, 399);
        getContentPane().add(tabbedPane);


        JPanel userChange = new UserChangePane(jsonPaths.getUserJsonPath());
        tabbedPane.addTab("User Configuration", null, userChange, null);

        JPanel serverChange = new ServerChangePane(jsonPaths.getApplicationPropertiesPath());
        tabbedPane.addTab("Application Properties", null, serverChange, null);

        JPanel filterManager = new FilterManagePane(jsonPaths.getFiltersPath());
        tabbedPane.addTab("Filters",null,filterManager,null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setVisible(true);
    }

}
