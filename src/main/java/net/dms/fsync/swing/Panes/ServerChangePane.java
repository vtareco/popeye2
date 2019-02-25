package net.dms.fsync.swing.Panes;

import net.dms.fsync.swing.EverisManager;
import net.dms.fsync.swing.components.Toast;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ServerChangePane extends JPanel {


    private JTextField fenixServerJtf, jiraServerJtf, proxyHostJtf, proxyPortJtf;
    private JFileChooser pathJfc;
    private JButton acceptChangesJbtn, declineChangesJbtn, openFileChooser;
    private JLabel proxyJl,fenixServerJl,jiraServerJl,currentPathJl;


    public ServerChangePane(String path) {
        setLayout(null);
        this.setSize(600, 400);
      this.setName("ApplicationProperties");
        loadPane(path);
    }


    public void loadPane(String path) {
        LocalVariables lv = new LocalVariables();
        pathJfc = new JFileChooser();
        fenixServerJtf = new JTextField();
        jiraServerJtf = new JTextField();
        proxyHostJtf = new JTextField();
        proxyPortJtf = new JTextField();
        acceptChangesJbtn = new JButton();
        declineChangesJbtn = new JButton();
        openFileChooser = new JButton();
        currentPathJl = new JLabel();
        fenixServerJl = new JLabel();
        jiraServerJl = new JLabel();
        proxyJl = new JLabel();
        ApplicationProperties ap = lv.getApFromJson(path);
        labelConfigutationLoader();
        buttonConfigurationLoad(ap.getWorkingDirectory());
        textFieldConfigurationLoader(ap);
        pathConfigurationLoader(ap);





        acceptChangesJbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeServerFunction(ap,path);

                //JOptionPane.showMessageDialog(null,"Success","Information",1,null);
                Toast toast = new Toast("Success",Toast.ToastType.INFO);
                toast.setVisible(true);
            }
        });



        openFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathChooserFunction(ap);
            }
        });

        openFileChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                openFileChooser.setText("Choose new directory");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                openFileChooser.setText("Current path: " + ap.getWorkingDirectory());
            }
        });

        this.setVisible(true);
    }


   private void  changeServerFunction(ApplicationProperties ap,String path){
       LocalVariables lv = new LocalVariables();

       ap.setFenixUrl(fenixServerJtf.getText());
       ap.setJiraUrl(jiraServerJtf.getText());
       ap.setProxyHost(proxyHostJtf.getText());
       ap.setProxyHost(proxyHostJtf.getText());
       ap.setProxyPort(proxyPortJtf.getText());
        lv.serverConfiguration(ap,path);
    // lv.alterConf(js);

    }

    private void textFieldConfigurationLoader(ApplicationProperties ap) {
        fenixServerJtf.setText(ap.getFenixUrl());
        fenixServerJtf.setBounds(160,30,300,25);
        fenixServerJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(fenixServerJtf);


        jiraServerJtf.setText(ap.getJiraUrl());
        jiraServerJtf.setBounds(160,90,300,25);
        jiraServerJtf.setFont(new Font("Arial", Font.PLAIN, 16));
       this.add(jiraServerJtf);


        proxyHostJtf.setText(ap.getProxyHost());
        proxyHostJtf.setBounds(160,150,300,25);
        proxyHostJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(proxyHostJtf);

        proxyPortJtf.setText(ap.getProxyPort());
        proxyPortJtf.setBounds(480,150,50,25);
        proxyPortJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(proxyPortJtf);


    }

    private void labelConfigutationLoader() {

        fenixServerJl.setFont(new Font("Arial", Font.PLAIN, 16));
        fenixServerJl.setText("Fenix URL: ");
        fenixServerJl.setHorizontalAlignment(JLabel.LEFT);
        fenixServerJl.setBounds(20,30,150,25);
        this.add(fenixServerJl);

        jiraServerJl.setFont(new Font("Arial", Font.PLAIN, 16));
        jiraServerJl.setText("Jira URL: ");
        jiraServerJl.setHorizontalAlignment(JLabel.LEFT);
        jiraServerJl.setBounds(20,90,150,25);
        this.add(jiraServerJl);

        proxyJl.setFont((new Font("Arial", Font.PLAIN, 16)));
        proxyJl.setText("Proxy URL & Port : ");
        proxyJl.setHorizontalAlignment(JLabel.LEFT);
        proxyJl.setBounds(20,150,150,25);
        this.add(proxyJl);

    }


    private void pathConfigurationLoader(ApplicationProperties ap) {

        pathJfc.setCurrentDirectory(new File(ap.getWorkingDirectory()));
        pathJfc.setDialogTitle("Working path");
        pathJfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        pathJfc.setAcceptAllFileFilterUsed(false);

    }


    private void buttonConfigurationLoad(String path) {
        openFileChooser.setText("Current path: " + path );
        openFileChooser.setBounds(200, 230, 200, 30);
        this.add(openFileChooser);


        acceptChangesJbtn.setText("Save");
        acceptChangesJbtn.setBounds(30, 300, 110, 30);
        this.add(acceptChangesJbtn);


        declineChangesJbtn.setText("Cancel");
        declineChangesJbtn.setBounds(455, 300, 110, 30);
       // this.add(declineChangesJbtn);
    }


    private void pathChooserFunction(ApplicationProperties ap) {

        if (pathJfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            ap.setWorkingDirectory(pathJfc.getSelectedFile().toString());

            buttonConfigurationLoad(ap.getWorkingDirectory());
        } else {
            System.out.println("No Selection ");
        }


    }


}

