package net.dms.fsync.httphandlers.control.actions;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.dms.fsync.httphandlers.control.ActionExecutor;
import net.dms.fsync.httphandlers.entities.ActionResponse;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * Created by dminanos on 17/04/2017.
 */
public class DownloadAction {

	private String actionFileName;
	private String downloadedPathFile;
	private Map<String, String> variables = new HashMap<>();

	public DownloadAction(String actionFileName, String downloadedPathFile, Map<String, String> variables) {
		this.actionFileName = actionFileName;
		this.downloadedPathFile = downloadedPathFile;
		this.variables = variables;
	}

	public void execute() {
		ActionExecutor ae = new ActionExecutor(actionFileName, variables);
		ActionResponse ar = ae.execute();
		HttpResponse httpResponse = (HttpResponse) ar.getResponse();
		InputStream in = null;
		OutputStream os = null;
		try {
			HttpEntity entity2 = httpResponse.getEntity();
			in = entity2.getContent();
			os = new FileOutputStream(this.downloadedPathFile);
			IOUtils.copy(in, os);
		} catch(Exception ex) {
			throw new AppException(ex);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(os);
		}
	}
}
