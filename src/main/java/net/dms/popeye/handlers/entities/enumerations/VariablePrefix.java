package net.dms.popeye.handlers.entities.enumerations;

/**
 * Created by dminanos on 13/04/2017.
 */
public enum VariablePrefix {
    RESPONSE_HEADER("response.header.");

    private String prefix;

    VariablePrefix(String prefix){
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }


}
