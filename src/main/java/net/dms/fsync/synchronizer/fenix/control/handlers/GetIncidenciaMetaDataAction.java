package net.dms.fsync.synchronizer.fenix.control.handlers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.dms.fsync.httphandlers.control.ActionExecutor;
import net.dms.fsync.httphandlers.entities.ActionResponse;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.IncidenciasMetaDataType;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * Created by dminanos on 17/04/2017.
 */
public class GetIncidenciaMetaDataAction {

	private Map<IncidenciasMetaDataType, Map> metadataMaps = new HashMap<>();
	private Map<String, String> variables = new HashMap<>();

	public GetIncidenciaMetaDataAction(Map<String, String> variables) {
		this.variables = variables;
	}

	public void execute() {
		ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_incidencias_tarea_causante.xml", variables);
		ActionResponse ar = ae.execute();
		System.out.println("3 passo");
		HttpResponse httpResponse = (HttpResponse) ar.getResponse();
		InputStream in = null;
		OutputStream os = null;
		try {
			HttpEntity entity2 = httpResponse.getEntity();
			in = entity2.getContent();
			String content = IOUtils.toString(in);
			Map<String, String> mapTareasCausantes = getTareasCausantes(content);
			metadataMaps.put(IncidenciasMetaDataType.TAREA_CAUSANTE, mapTareasCausantes);
			Map<String, String> mapAccCorrector = getAccCorrector(content);
			metadataMaps.put(IncidenciasMetaDataType.ACC_CORRECTOR, mapAccCorrector);
			System.out.println("Ligação incidencias");
		} catch(Exception ex) {
			throw new AppException(ex);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(os);
		}
	}

	private Map<String, String> getTareasCausantes(String content) {
		Map<String, String> tareas = new HashMap<>();
		int position = content.indexOf("<select name='tareaCausante'");
		content = content.substring(position, content.length());
		position = content.indexOf("</select>");
		content = content.substring(0, position);
		String code;
		String description;
		while(position >= 0) {
			position = content.indexOf("<option value='");
			if(position < 0) {
				break;
			}
			content = content.substring(position + 15, content.length());
			position = content.indexOf("'");
			code = content.substring(0, position);
			position = content.indexOf(">");
			content = content.substring(position, content.length());
			position = content.indexOf("</option>");
			description = content.substring(1, position);
			tareas.put(code, description);
		}
		return tareas;
	}

	private Map<String, String> getAccCorrector(String content) {
		Map<String, String> tareas = new HashMap<>();
		int position = content.indexOf("<select name='accCorrector'");
		content = content.substring(position, content.length());
		position = content.indexOf("</select>");
		content = content.substring(0, position);
		String code;
		String description;
		while(position >= 0) {
			position = content.indexOf("<option value='");
			if(position < 0) {
				break;
			}
			content = content.substring(position + 15, content.length());
			position = content.indexOf("'");
			code = content.substring(0, position);
			position = content.indexOf(">");
			content = content.substring(position, content.length());
			position = content.indexOf("</option>");
			description = content.substring(1, position);
			tareas.put(code, description);
		}
		return tareas;
	}

	public Map<IncidenciasMetaDataType, Map> getMetadataMaps() {
		return metadataMaps;
	}
}
