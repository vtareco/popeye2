package net.dms.fsync.settings;

import java.util.Locale;
import java.util.ResourceBundle;

public class Internationalization {

    static ResourceBundle bundle;

    public static void init(String languageSelected) {
        bundle = ResourceBundle.getBundle("i18n/language" ,new Locale(languageSelected,languageSelected.toUpperCase()));
        System.out.println(bundle.getBaseBundleName());
    }

    public static String getStringTranslated(String key) {
        return bundle.getString(key);
    }

}
