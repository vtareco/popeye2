package net.dms.popeye.handlers.jfsynchronizer.fenix.entities.exceptions;

import net.dms.popeye.handlers.entities.exceptions.AppException;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixACCUploadResponseItem;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixIncidenciaUploadResponseItem;

import java.util.List;
import java.util.Set;

/**
 * Created by dminanos on 26/04/2017.
 */
public class FenixIncidenciaUploadAppException extends AppException{
    public FenixIncidenciaUploadAppException(Throwable ex) {
        super(ex);
    }

    public FenixIncidenciaUploadAppException(List<FenixIncidenciaUploadResponseItem> fenixIncidenciaUploadItems) {

       StringBuilder sb = new StringBuilder();
       for (FenixIncidenciaUploadResponseItem item : fenixIncidenciaUploadItems){
           sb.append(item);
           sb.append("\n");
       }
       throw new FenixIncidenciaUploadAppException(sb.toString());
    }
    public FenixIncidenciaUploadAppException(String msg){
        super(msg);
    }
}
