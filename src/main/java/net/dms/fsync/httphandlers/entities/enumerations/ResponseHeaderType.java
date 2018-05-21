package net.dms.fsync.httphandlers.entities.enumerations;

/**
 * Created by dminanos on 13/04/2017.
 */
public enum ResponseHeaderType {
    SET_COOKIE("Set-Cookie");

    private String name;

    ResponseHeaderType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
