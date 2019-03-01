package net.dms.fsync.synchronizer.fenix.entities.exceptions;

import java.util.Set;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.synchronizer.fenix.entities.FenixACCUploadResponseItem;

/**
 * Created by dminanos on 26/04/2017.
 */
public class FenixAccUploadAppException extends AppException {

	public FenixAccUploadAppException(Throwable ex) {
		super(ex);
	}

	public FenixAccUploadAppException(Set<FenixACCUploadResponseItem> fenixACCUploadItems) {
		StringBuilder sb = new StringBuilder();
		for(FenixACCUploadResponseItem item : fenixACCUploadItems) {
			sb.append(item);
			sb.append("\n");
		}
		throw new FenixAccUploadAppException(sb.toString());
	}

	public FenixAccUploadAppException(String msg) {
		super(msg);
	}
}
