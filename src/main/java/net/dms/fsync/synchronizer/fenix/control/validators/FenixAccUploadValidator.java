package net.dms.fsync.synchronizer.fenix.control.validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dms.fsync.httphandlers.control.validators.HttpResponseValidator;
import net.dms.fsync.httphandlers.control.validators.ValidatorContext;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.synchronizer.fenix.entities.FenixACCUploadResponse;
import net.dms.fsync.synchronizer.fenix.entities.exceptions.FenixAccUploadAppException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * Created by dminanos on 19/04/2017.
 */
public class FenixAccUploadValidator implements HttpResponseValidator {

	public void validate(HttpResponse response, ValidatorContext validatorContext) {
		final String BEGIN_TEXT = "var jsonResultado = new dojo.data.ItemFileWriteStore({data: eval('(' + '";
		final String END_TEXT = "' + ')')});";
//TODO FIXME
		HttpEntity entity2 = response.getEntity();
		try {
			InputStream in = entity2.getContent();
			String responseStr = IOUtils.toString(in);
			System.out.println("response: " + responseStr);
			int begin = responseStr.indexOf(BEGIN_TEXT) + BEGIN_TEXT.length();
			int end = responseStr.indexOf(END_TEXT);
			String result = responseStr.substring(begin, end);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			FenixACCUploadResponse uploadResponse = mapper.readValue(result, FenixACCUploadResponse.class);
			if(uploadResponse.getItems().stream().filter(i -> Boolean.TRUE.equals(i.getError())).count() > 0) {
				throw new FenixAccUploadAppException(
				  uploadResponse.getItems().stream().filter(i -> Boolean.TRUE.equals(i.getError())).collect(Collectors.toSet()));
			}
		} catch(IOException e) {
			throw new AppException(e);
		}
	}
}
