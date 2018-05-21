package net.dms.fsync.synchronizer.fenix.entities.exceptions;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.synchronizer.fenix.entities.FenixIncidenciaUploadResponseItem;

import java.util.List;

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
