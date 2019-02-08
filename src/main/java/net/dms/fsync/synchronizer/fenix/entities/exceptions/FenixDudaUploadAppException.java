package net.dms.fsync.synchronizer.fenix.entities.exceptions;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.synchronizer.fenix.entities.FenixIncidenciaUploadResponseItem;

import java.util.List;

/**
 * Created by vics on 08/02/2019.
 */
public class FenixDudaUploadAppException extends AppException{
    public FenixDudaUploadAppException(Throwable ex) {
        super(ex);
    }

    public FenixDudaUploadAppException(List<FenixIncidenciaUploadResponseItem> fenixIncidenciaUploadItems) {

       StringBuilder sb = new StringBuilder();
       for (FenixIncidenciaUploadResponseItem item : fenixIncidenciaUploadItems){
           sb.append(item);
           sb.append("\n");
       }
       throw new FenixDudaUploadAppException(sb.toString());
    }
    public FenixDudaUploadAppException(String msg){
        super(msg);
    }
}
