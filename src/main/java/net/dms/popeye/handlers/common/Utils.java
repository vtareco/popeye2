package net.dms.popeye.handlers.common;

/**
 * Created by dminanos on 18/04/2017.
 */
public class Utils {
    public static String replaceVariable(String key, String mapValue, String value){
        return value.replaceAll("\\$\\{" + key + "\\}", mapValue);
    }
}
