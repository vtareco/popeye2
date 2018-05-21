package net.dms.fsync.swing.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class PopClickListener extends MouseAdapter {
	JMenuItem[] items;

	public PopClickListener(JMenuItem[] items) {
		this.items = items;
	}

	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger())
			doPop(e);
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger())
			doPop(e);
	}

	private void doPop(MouseEvent e) {
		GenericPopup menu = new GenericPopup(items);
		menu.show(e.getComponent(), e.getX(), e.getY());
	}
	
	class GenericPopup extends JPopupMenu {
		public GenericPopup(JMenuItem[] items) {
			for (JMenuItem item : items) {
				add(item);
			}
		}
	}
}
