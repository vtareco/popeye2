package net.dms.fsync.swing.Panes;

import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.JenixSettings;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserChangePane extends JPanel {

    private JTextField fenixUserJtf, jiraUserJtf;
    private JPasswordField fenixPasswordJtf, jiraPasswordJtf;
    private JButton acceptChangesJbtn, declineChangesJbtn, reReloadChanges, clearAllBtn;
    private JLabel jiraCredentialsJl;
    private JLabel fenixCredentialsJl;
    private String cacheFenixUser;
    private String cacheFenixPw;
    private String cacheJiraUser;
    private String cacheJiraPw;

/*
    public UserChangePane JPanel panelparent, File jsonUserCrete) {
        setLayout(null);
        this.setSize(600, 400);
        setTitle("User Settings");
        setLocationRelativeTo(panelparent);
        setModal(true);
        setResizable(false);
        preLoadDialog(jsonUserCrete);

    }

    private void preLoadDialog(File jsonUserCrete) {
        UserChange uc = new UserChange();
        uc.setFenixPassword("");
        uc.setFenixUser("");
        uc.setJiraPassword("");
        uc.setFenixUser("");
        loadPane(uc);
    }
*/

    public UserChangePane(JenixSettings js) {
        setLayout(null);
        loadPane(js);
    }


    private void loadPane(JenixSettings js ) {
        jiraCredentialsJl = new JLabel();
        fenixCredentialsJl = new JLabel();
        fenixUserJtf = new JTextField();
        fenixPasswordJtf = new JPasswordField();
        jiraUserJtf = new JTextField();
        jiraPasswordJtf = new JPasswordField();
        acceptChangesJbtn = new JButton();
        declineChangesJbtn = new JButton();
        reReloadChanges = new JButton();
        clearAllBtn = new JButton();
        cacheFenixUser = js.getUc().getFenixUser();
        cacheFenixPw = js.getUc().getFenixPassword();
        cacheJiraUser = js.getUc().getJirauser();
        cacheJiraPw = js.getUc().getJiraPassword();


        fenixConfigurationLoad(js.getUc().getFenixUser(), js.getUc().getFenixPassword());
        jiraConfigurationLoad(js.getUc().getJirauser(), js.getUc().getJiraPassword());
        buttonConfigurationLoad();

        acceptChangesJbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeUserFunction(js);
            }
        });

        declineChangesJbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelUserFunction();
            }
        });

        reReloadChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadChanges(cacheFenixUser, cacheFenixPw, cacheJiraUser, cacheJiraPw);

            }
        });

        clearAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        setVisible(true);

    }

    public void clearFields() {
        fenixUserJtf.setText(null);
        fenixPasswordJtf.setText(null);
        jiraUserJtf.setText(null);
        jiraPasswordJtf.setText(null);
    }


    public void changeUserFunction(JenixSettings js) {
        //VariableService vl = new VariableService();
        LocalVariables lv = new LocalVariables();
        js.getUc().setFenixUser(fenixUserJtf.getText());
        js.getUc().setFenixPassword(fenixPasswordJtf.getText());
        js.getUc().setJirauser(jiraUserJtf.getText());
        js.getUc().setJiraPassword(jiraPasswordJtf.getText());
       // System.out.println(fenixUserJtf.getText());



        lv.alterConf(js);

        // vl.writeUserConfJsonFile(uc);

    }

    public void cancelUserFunction() {

    }

    private void reloadChanges(String cacheFenixUser, String cacheFenixPw, String cacheJiraUser, String cacheJiraPw) {
        fenixUserJtf.setText(cacheFenixUser);
        fenixPasswordJtf.setText(cacheFenixPw);
        jiraUserJtf.setText(cacheJiraUser);
        jiraPasswordJtf.setText(cacheJiraPw);

    }


    private void fenixConfigurationLoad(String user, String password) {

        fenixCredentialsJl.setText(" Fenix Credentials");
        fenixCredentialsJl.setHorizontalAlignment(JLabel.CENTER);
        fenixCredentialsJl.setFont(new Font("Arial", Font.BOLD, 16));
        fenixCredentialsJl.setBounds(200, 7, 230, 14);
        this.add(fenixCredentialsJl);

        fenixUserJtf.setText(user);
        fenixUserJtf.setHorizontalAlignment(JTextField.CENTER);
        fenixUserJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        fenixUserJtf.setBounds(200, 35, 230, 25);
        this.add(fenixUserJtf);
        fenixPasswordJtf.setText(password);
        fenixPasswordJtf.setHorizontalAlignment(JTextField.CENTER);
        fenixPasswordJtf.setBounds(200, 65, 230, 25);
        this.add(fenixPasswordJtf);

    }

    private void jiraConfigurationLoad(String user, String password) {
        jiraCredentialsJl.setText(" Jira Credentials");
        jiraCredentialsJl.setHorizontalAlignment(JLabel.CENTER);
        jiraCredentialsJl.setFont(new Font("Arial", Font.BOLD, 16));
        jiraCredentialsJl.setBounds(200, 150, 230, 14);
        this.add(jiraCredentialsJl);


        jiraUserJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        jiraUserJtf.setBounds(200, 175, 230, 25);
        jiraUserJtf.setHorizontalAlignment(JTextField.CENTER);
        jiraUserJtf.setText(user);
        this.add(jiraUserJtf);
        jiraPasswordJtf.setText(password);
        jiraPasswordJtf.setHorizontalAlignment(JTextField.CENTER);
        jiraPasswordJtf.setBounds(200, 205, 230, 25);
        this.add(jiraPasswordJtf);
    }


    private void buttonConfigurationLoad() {
        acceptChangesJbtn.setName("acceptChangesBtn");
        acceptChangesJbtn.setText("Save");
        acceptChangesJbtn.setBounds(30, 300, 110, 30);
        this.add(acceptChangesJbtn);

        declineChangesJbtn.setName("declineChangesBtn");
        declineChangesJbtn.setText("Cancel");
        declineChangesJbtn.setBounds(455, 300, 110, 30);
       // this.add(declineChangesJbtn);

        reReloadChanges.setFont(new Font("Arial", Font.PLAIN, 10));
        reReloadChanges.setName("reloadChanges");
        reReloadChanges.setText("Re-Writte");
        reReloadChanges.setBounds(470, 7, 100, 25);
        this.add(reReloadChanges);

        clearAllBtn.setFont(new Font("Arial", Font.PLAIN, 10));
        clearAllBtn.setName("clearFields");
        clearAllBtn.setText("Clear Text Fields");
        clearAllBtn.setBounds(200, 230, 230, 25);
        this.add(clearAllBtn);
    }


}
