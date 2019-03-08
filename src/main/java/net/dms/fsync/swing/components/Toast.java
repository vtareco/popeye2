package net.dms.fsync.swing.components;

import net.dms.fsync.httphandlers.entities.config.ResponseValidator;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Toast-like behaviour for swing
 */
public class Toast extends JDialog {
    private final String toastString;


    //private int miliseconds;

    public Toast(String toastString, ToastType toastType) {
        this.toastString = toastString;
        //this.miliseconds = time;

        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout(0, 0));
        JPanel panel = setupContainer(toastType);
        JLabel toastLabel = setupMessageLabel(this.toastString);
        setBounds(panel, toastLabel);
        destroyWhenTimeIsOver();
        setLocationRelativeTo(WorkingJira.getMainJframe());

    }

    public static void display (String toastString, ToastType toastType){
        Toast toast = new Toast(toastString, toastType);
        toast.setModal(true);
        toast.setVisible(true);
       /* Window parentWindow = SwingUtilities.windowForComponent(toast);
        JDialog popUp = new JDialog(parentWindow);
        popUp.setLocationRelativeTo(WorkingJira.getMain());
        //popUp.setModal(true);
        popUp.pack();
        popUp.setVisible(true);*/
      /*  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - WorkingJira.getMain().getWidth();
        int y = 0;*/
        //toast.setLocation(x,y);
       // toast.setLocationRelativeTo
       // toast.setVisible(true);

    }

    /**
     * Setup and create a container for the childrenç
     *
     * @return A new {@link JPanel} instance
     */
    private JPanel setupContainer(ToastType toastType) {
        JPanel panel = new JPanel();
        panel.setBackground(toastType.bkgColor);
        panel.setBorder(new LineBorder(toastType.lineColor, 2));
        getContentPane().add(panel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Setup the message provided to appear in the content area
     *
     * @param toastString The message to display
     * @return A new {@link JLabel} instance
     */
    private JLabel setupMessageLabel(String toastString) {
        JLabel toastLabel = new JLabel("");
        toastLabel.setText(toastString);
        toastLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        toastLabel.setForeground(Color.BLACK);
        return toastLabel;
    }

    /**
     * Change the bounds of this toast
     *
     * @param panel      The panel that will change size
     * @param toastLabel The label to show
     */
    private void setBounds(JPanel panel, JLabel toastLabel) {
        setSize(toastLabel.getPreferredSize().width + 20, 31);
        //setAlwaysOnTop(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation(dim.width / 2 - getSize().width / 2, 0);
        //setLocation(1093 / 2, 465 / 2);
        panel.add(toastLabel);
       //pack();


      /*  Window parentWindow = SwingUtilities.windowForComponent(this);
        Toast popUp = new Toast(parentWindow);
        popUp.setLocationRelativeTo(WorkingJira.getMain());
        popUp.setModal(true);
        popUp.pack();
        popUp.setVisible(true);*/




      //  int x = panel.getWidth();
       // int y =panel.getHeight();
       // System.out.println("Largura "+x+" Altura "+y);

    }

    /**
     * Kill this toast when the timer is over
     */
    private void destroyWhenTimeIsOver() {
        new Thread(() -> {
            try {
                for (double d = 1.0; d > 0.2; d -= 0.1) {
                    if(toastString.length() >= 10){
                        Thread.sleep(toastString.length() * 8);
                    }else{
                        Thread.sleep(toastString.length() * 25);
                    }

                    setOpacity((float) d);
                }
                //Thread.sleep(miliseconds);
                dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public enum ToastType {
        ERROR(Color.RED, MyColors.TOAST_ERROR_LINE),
        INFO(MyColors.TOAST_INFO_COLOR, Color.BLACK),
        WARNING(Color.YELLOW, Color.BLACK);

        private final Color bkgColor;
        private final Color lineColor;

        ToastType(Color bkgColor, Color lineColor) {
            this.bkgColor = bkgColor;
            this.lineColor = lineColor;
        }
    }

}