package net.dms.fsync.httphandlers.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import net.dms.fsync.httphandlers.control.validators.HttpResponseValidator;
import net.dms.fsync.httphandlers.control.validators.ValidatorContext;
import net.dms.fsync.httphandlers.entities.ActionResponse;
import net.dms.fsync.httphandlers.entities.config.Action;
import net.dms.fsync.httphandlers.entities.config.Parameter;
import net.dms.fsync.httphandlers.entities.config.ProxyConfiguration;
import net.dms.fsync.httphandlers.entities.config.ResponseValidator;
import net.dms.fsync.httphandlers.entities.enumerations.ResponseHeaderType;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicNameValuePair;

import static net.dms.fsync.httphandlers.entities.enumerations.VariablePrefix.RESPONSE_HEADER;

/**
 * Created by dminanos on 11/04/2017.
 */
public class HttpHandler {

	private Action action;
	private HttpClient client;
	private Map<String, String> variables;
	private ProxyConfiguration proxyConfiguration;

	public HttpHandler(Action action, HttpClient client, ProxyConfiguration proxyConfiguration, Map<String, String> variables) {
		this.action = action;
		this.client = client;
		this.variables = variables;
		this.proxyConfiguration = proxyConfiguration;
	}

	public ActionResponse execute() {
		action.setUrl(processVariable(action.getUrl()));
		for(Parameter p : action.getParameters()) {
			p.setValue(processVariable(p.getValue()));
		}
		ActionResponse actionResponse = null;
		try {
			HttpRequestBase request = null;
			switch(action.getHttpMethod()) {
				case POST:
					//request = new HttpPost(action.getUrl());
					request = new HttpPost(action.getUrl());
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					for(Parameter parameter : action.getParameters().stream().filter(p -> p.getType() == null).collect(Collectors.toList())) {
						nvps.add(new BasicNameValuePair(parameter.getName(), parameter.getValue()));
					}
					if(nvps.size() > 0) {
						((HttpPost) request).setEntity(new UrlEncodedFormEntity(nvps));
					}
					// TODO FIXME critical
					for(Parameter parameter : action.getParameters().stream().filter(p -> "file".equals(p.getType())).collect(Collectors.toList())) {
						MultipartEntityBuilder builder = MultipartEntityBuilder.create();
						builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
						File file = new File(parameter.getValue());
						// File file = new File("H:/Proyectos_2/BMW/RSP/Fenix/959829-Maintenance RSP - April/959829-accs.xlsx");
						FileBody fileBody =
						  new FileBody(file, ContentType.create("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), file.getName());
						// FileBody fileBody = new FileBody(file, ContentType.create("application/vnd.ms-excel"), file.getName());
						builder.addPart(parameter.getName(), fileBody);
						HttpEntity entity = builder.build();
						((HttpPost) request).setEntity(entity);
					}
					break;
				case GET:
					request = new HttpGet(action.getUrl());
					break;
				default:
					throw new RuntimeException("HTTP method not implemented");
			}
			for(net.dms.fsync.httphandlers.entities.config.Header header : action.getHeaders()) {
				request.setHeader(header.getName(), processVariable(header.getValue()));
			}
			Map<String, String> variablesResponse = new HashMap<String, String>();
			System.out.println("request: " + Arrays.toString(request.getAllHeaders()));
			if(proxyConfiguration != null) {
				HttpHost proxy = new HttpHost(proxyConfiguration.getHost(), proxyConfiguration.getPort(), proxyConfiguration.getScheme());
				RequestConfig config = RequestConfig.custom()
										 .setProxy(proxy)
										 .build();
				request.setConfig(config);
			}
			HttpResponse httpResponse = client.execute(request);
			validate(httpResponse);
			for(org.apache.http.Header header : httpResponse.getAllHeaders()) {
				String extraName;
				if(ResponseHeaderType.SET_COOKIE.getName().equals(header.getName())) {
					String[] cookies = header.getValue().split(";");
					for(String cookie : cookies) {
						String[] keyValue = cookie.split("=");
						if(keyValue.length == 1) {
							variablesResponse.put(RESPONSE_HEADER.getPrefix() + header.getName() + "." + keyValue[0], null);
						} else {
							variablesResponse.put(RESPONSE_HEADER.getPrefix() + header.getName() + "." + keyValue[0], keyValue[1]);
						}
					}
				} else {
					variablesResponse.put(RESPONSE_HEADER.getPrefix() + header.getName(), header.getValue());
				}
			}
			actionResponse = new ActionResponse();
			actionResponse.setResponse(httpResponse);
			actionResponse.getVariables().putAll(variablesResponse);
			System.out.println("variablesResponse: " + variablesResponse);
			System.out.println("httpResponse: " + httpResponse.getEntity());
			System.out.println("httpResponse: " + httpResponse);
		} catch(IOException e) {
			throw new AppException(e);
		}
		return actionResponse;
	}

	// TODO FIXME - REMOVE, variables are replaced in action executor
	private String processVariable(String value) {
		for(String key : variables.keySet()) {
			String mapValue = variables.get(key);
			System.out.println(">key:  " + key + ", value: " + mapValue);
			if(value != null) {
				try {
					value = value.replaceAll("\\$\\{" + key + "\\}", mapValue != null
																	   ? Matcher.quoteReplacement(mapValue)
																	   : null);
				} catch(Exception ex) {
					System.out.println("error in process value");
					ex.printStackTrace();
					throw new AppException(ex);
				}
			}
		}
		System.out.println("value: " + value);
		return value;
	}

	private void validate(HttpResponse response) {
		switch(response.getStatusLine().getStatusCode()) {
			case HttpStatus.SC_OK:
				break;
			case HttpStatus.SC_MOVED_TEMPORARILY:
				break;
			default:
				throw new AppException(String.format("HTTP Error, HTTP Status: %d", response.getStatusLine().getStatusCode()));
		}
		try {
			for(ResponseValidator validator : action.getValidators()) {
				ValidatorContext validatorContext = new ValidatorContext();
				validatorContext.setParameters(validator.getParameters());
				Class clazz = Class.forName(validator.getClassName());
				HttpResponseValidator rv = (HttpResponseValidator) clazz.newInstance();
				rv.validate(response, validatorContext);
			}
		} catch(ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			throw new AppException(e);
		}
	}
}
