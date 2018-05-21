package net.dms.fsync.httphandlers.control.validators;

import net.dms.fsync.httphandlers.entities.config.Parameter;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

/**
 * Created by dminanos on 15/04/2017.
 */
public class ContentTypeValidator implements HttpResponseValidator{
    public void validate(HttpResponse response, ValidatorContext validatorContext) {
        boolean valid = false;
        Header[] headers = response.getHeaders("Content-Type");
        final String contentType;
        if (headers.length == 1){
            contentType = headers[0].getValue();
        }else{
            contentType = null;
        }
        if (contentType != null) {
            for (Parameter parameter : validatorContext.getParameters()) {
                if (parameter.getValue().equals(contentType)) {
                    ;
                    valid = true;
                    break;
                }
            }
        }
        if(!valid){
            throw new AppException("Content type not valid: "+ contentType);
        }

    }
}
