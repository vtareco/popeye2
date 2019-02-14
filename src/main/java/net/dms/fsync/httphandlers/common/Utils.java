package net.dms.fsync.httphandlers.common;

import java.io.*;

/**
 * Created by dminanos on 18/04/2017.
 */
public class Utils {
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
}
