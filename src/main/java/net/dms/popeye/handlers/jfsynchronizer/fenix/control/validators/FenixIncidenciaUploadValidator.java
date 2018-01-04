package net.dms.popeye.handlers.jfsynchronizer.fenix.control.validators;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dms.popeye.handlers.control.validators.HttpResponseValidator;
import net.dms.popeye.handlers.control.validators.ValidatorContext;
import net.dms.popeye.handlers.entities.exceptions.AppException;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixACCUploadResponse;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixIncidencia;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixIncidenciaUploadResponse;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.exceptions.FenixAccUploadAppException;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.exceptions.FenixIncidenciaUploadAppException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 19/04/2017.
 */
public class FenixIncidenciaUploadValidator implements HttpResponseValidator {
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


            FenixIncidenciaUploadResponse uploadResponse = mapper.readValue(result, FenixIncidenciaUploadResponse.class);

            if (uploadResponse.getItems().stream().filter(i -> Boolean.TRUE.equals(i.getEsError())).count() > 0){

                throw new FenixIncidenciaUploadAppException(uploadResponse.getItems());
            }


        } catch (IOException e) {
            throw new AppException(e);
        }

    }
}
