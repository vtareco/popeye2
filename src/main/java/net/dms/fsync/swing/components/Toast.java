package net.dms.fsync.swing.components;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * Toast-like behaviour for swing
 */
public class Toast extends JDialog {

	//private int miliseconds;

	public Toast(String toastString,ToastType toastType) {
		//this.miliseconds = time;

		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panel = setupContainer(toastType);
		JLabel toastLabel = setupMessageLabel(toastString);
		setBounds(panel, toastLabel);
		destroyWhenTimeIsOver();
	}

	/**
	 * Setup and create a container for the children
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
	 *
	 * @return A new {@link JLabel} instance
	 */
	private JLabel setupMessageLabel(String toastString) {
		JLabel toastLabel = new JLabel("");
		toastLabel.setText(toastString);
		toastLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		toastLabel.setForeground(Color.WHITE);
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
		setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, 0);
		panel.add(toastLabel);
		setVisible(true);
	}

	/**
	 * Kill this toast when the timer is over
	 */
	private void destroyWhenTimeIsOver() {
		new Thread(() -> {
			try {
				for (double d = 1.0; d > 0.2; d -= 0.1) {
					Thread.sleep(200);
					setOpacity((float)d);
				}
				//Thread.sleep(miliseconds);
				dispose();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public enum ToastType {
		ERROR(Color.RED, MyColors.TOAST_ERROR_LINE),
		INFO(MyColors.TOAST_INFO_COLOR,Color.BLACK),
		WARNING(Color.YELLOW, Color.ORANGE);

		private final Color bkgColor;
		private final Color lineColor;

		ToastType(Color bkgColor, Color lineColor) {
			this.bkgColor = bkgColor;
			this.lineColor = lineColor;
		}
	}

}