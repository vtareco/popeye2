package net.dms.popeye.handlers.jfsynchronizer.swing.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class JenixDialog <E> extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = -8851951069056128059L;


    private E payload;

    protected JenixDialog(Component parent){
        setLocationRelativeTo(parent);
    }

    public Component getDlgComponent(){
        return this;
    }

    public JenixDialog(Component parent, E initialPayload){
        this(parent);
        this.payload = initialPayload;
        buildDialog();
    }

    protected abstract void loadData() ;

    public abstract void edit();
    public abstract JComponent createCentralPanel();
    public abstract void fillPayLoad();
    public abstract void initialize();


    private void buildDialog(){
        try{
            initialize();
            initializeComponents();

        }catch(Exception ex){
ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Se ha producido un error durante inicialización del formulario", "Error", JOptionPane.ERROR_MESSAGE);
        }


        loadData();

        edit();
    }


    protected void initializeComponents(){
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createCentralPanel(), BorderLayout.CENTER);
        getContentPane().add(createButtons(), BorderLayout.PAGE_END);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowListener(){

            public void windowOpened(WindowEvent arg0) {

            }

            public void windowClosing(WindowEvent windowEvent) {
                onCancel();
            }

            public void windowClosed(WindowEvent arg0) {

            }

            public void windowIconified(WindowEvent arg0) {

            }

            public void windowDeiconified(WindowEvent arg0) {

            }

            public void windowActivated(WindowEvent arg0) {

            }

            public void windowDeactivated(WindowEvent arg0) {

            }

        });

    }

    public E getPayload(){
        return payload;
    }
    public void setPayload(E payload){
        this.payload = payload;
    }
    protected JComponent createButtons(){
        JPanel panel = new JPanel();
        JButton btnAccept = new JButton("Aceptar");
        btnAccept.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                onAccept();
            }

        });
        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                onCancel();
            }

        });
        panel.add(btnAccept);
        panel.add(btnCancel);
        return panel;
    }



    protected void onCancel(){
        payload = null;
        dispose();
    }
    protected void onAccept(){
        try {
            fillPayLoad();
            dispose();

        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
