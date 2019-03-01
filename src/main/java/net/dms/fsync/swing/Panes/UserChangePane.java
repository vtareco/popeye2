package net.dms.fsync.swing.Panes;

import net.dms.fsync.swing.components.Toast;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
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
    private UserChange uc;

    public UserChangePane(String path) {
        setLayout(null);

        loadPane(path);
    }


    private void loadPane(String path) {
        LocalVariables lv = new LocalVariables();
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
        uc = lv.getUcFromJson(path);
        cacheFenixUser = uc.getFenixUser();
        cacheFenixPw = uc.getFenixPassword();
        cacheJiraUser = uc.getJirauser();
        cacheJiraPw = uc.getJiraPassword();


        fenixConfigurationLoad(uc.getFenixUser(), uc.getFenixPassword());
        jiraConfigurationLoad(uc.getJirauser(), uc.getJiraPassword());
        buttonConfigurationLoad();

        acceptChangesJbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                changeUserFunction(uc,path);
                //JOptionPane.showMessageDialog(null,"Success","Information",1,null);
                Toast.display("Success",Toast.ToastType.INFO);

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


    private void changeUserFunction(UserChange uc,String path) {
        LocalVariables lv = new LocalVariables();
        uc.setFenixUser(fenixUserJtf.getText());
        uc.setFenixPassword(fenixPasswordJtf.getText());
        uc.setJirauser(jiraUserJtf.getText());
        uc.setJiraPassword(jiraPasswordJtf.getText());
        lv.userConfiguration(uc,path);
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


        reReloadChanges.setFont(new Font("Arial", Font.PLAIN, 10));
        reReloadChanges.setName("reloadChanges");
        reReloadChanges.setText("Return");
        reReloadChanges.setBounds(470, 7, 100, 25);
        this.add(reReloadChanges);

        clearAllBtn.setFont(new Font("Arial", Font.PLAIN, 10));
        clearAllBtn.setName("clearFields");
        clearAllBtn.setText("Clear Text Fields");
        clearAllBtn.setBounds(200, 230, 230, 25);
        this.add(clearAllBtn);
    }


}
