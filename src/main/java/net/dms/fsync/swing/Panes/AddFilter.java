package net.dms.fsync.swing.Panes;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFilter extends JPanel {

	private JTextField textFilter;
	private JButton addFilter;
	private JLabel labelFilter, labelTitle;

	public AddFilter() {
		setLayout(null);
		loadPane();
	}

	private void loadPane() {
		labelFilter = new JLabel();
		labelTitle = new JLabel();
		textFilter = new JTextField();
		addFilter = new JButton();
		fenixConfigurationLoad();
		buttonConfigurationLoad();
		addFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		setVisible(true);
	}

	private void fenixConfigurationLoad() {
		textFilter = new JTextField();
		textFilter.setBounds(169, 154, 288, 20);
		this.add(textFilter);
		labelFilter.setText("Filters Configuration");
		labelFilter.setBounds(259, 51, 103, 14);
		this.add(labelFilter);
		labelTitle.setText("New Filter");
		labelTitle.setBounds(102, 154, 57, 20);
		this.add(labelTitle);
	}

	private void buttonConfigurationLoad() {
		addFilter.setName("acceptChangesBtn");
		addFilter.setText("Save");
		addFilter.setBounds(259, 258, 89, 23);
		this.add(addFilter);
	}
}
