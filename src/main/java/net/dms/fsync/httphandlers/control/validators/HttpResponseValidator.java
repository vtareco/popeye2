package net.dms.fsync.httphandlers.control.validators;

import org.apache.http.HttpResponse;

/**
 * Created by dminanos on 15/04/2017.
 */
public interface HttpResponseValidator {

	void validate(HttpResponse response, ValidatorContext validatorContext);
}
