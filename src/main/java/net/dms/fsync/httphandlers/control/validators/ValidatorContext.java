package net.dms.fsync.httphandlers.control.validators;

import java.util.List;

import net.dms.fsync.httphandlers.entities.config.Parameter;

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
