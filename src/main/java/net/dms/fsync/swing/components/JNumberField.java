package net.dms.fsync.swing.components;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JNumberField extends JTextField implements KeyListener, CaretListener {

	private int lastCaretPos;

	public JNumberField() {
		super();
		setExtras();
	}

	public JNumberField(int number) {
		super(Integer.toString(number));
		setExtras();
	}

	private void setExtras() {
		addKeyListener(this);
		addCaretListener(this);
	}

	public void caretUpdate(CaretEvent e) {
		lastCaretPos = e.getDot();
	}

	public void keyTyped(KeyEvent e) {
		e.consume();
	}

	public void keyPressed(KeyEvent e) {
		if(Character.isDigit(e.getKeyChar())) {
			if(isEditable()) {
				try {
					int cp = lastCaretPos;
					setText(getText().substring(0, lastCaretPos) + e.getKeyChar() + getText().substring(lastCaretPos, getText().length()));
					setCaretPosition(cp + 1);
				} catch(Exception es) {
				}
				e.consume();
			} else if(Character.isLetter(e.getKeyChar())) {
				e.consume();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		e.consume();
	}

	public int getNumber() {
		if(getText().equals("")) {
			return 0;
		}
		return Integer.parseInt(getText());
	}

	public void setNumber(int number) {
		setText(Integer.toString(number));
	}
}