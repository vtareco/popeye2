package net.dms.fsync.httphandlers.common;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by dminanos on 18/04/2017.
 */
public class Utils {
    public static String EMPTY_STRING = "";

    public static String replaceVariable(String key, String mapValue, String value) {
        return value.replaceAll("\\$\\{" + key + "\\}", mapValue);
    }

    public static File convertInputStreamToFileCommonWay(InputStream is, String pathname) throws IOException {
        OutputStream outputStream = null;
        try {
            File file = new File(pathname);
            outputStream = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            return file;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static String getProgramRoot() {
        URL url = Utils.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = null;
        try {
            jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return new File(jarPath).getParentFile().getPath();
    }

    public static String getSubtipo(String subtiposTarea) {
        if(StringUtils.isBlank(subtiposTarea)) {
            return EMPTY_STRING;
        }
        return subtiposTarea;
    }


}
