package net.dms.fsync.swing.components;

import net.dms.fsync.settings.Internationalization;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
    private int limit;

    public JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        } else {
            Toast.display(Internationalization.getStringTranslated("toastMaxChar") + limit, Toast.ToastType.WARNING);
        }

    }

}
