package net.dms.popeye.handlers.control.validators;

import net.dms.popeye.handlers.entities.config.Parameter;

import java.util.List;

/**
 * Created by dminanos on 15/04/2017.
 */
public class ValidatorContext {
    List<Parameter> parameters;

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
