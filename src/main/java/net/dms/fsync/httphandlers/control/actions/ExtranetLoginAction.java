package net.dms.fsync.httphandlers.control.actions;

import net.dms.fsync.httphandlers.control.ActionExecutor;
import net.dms.fsync.httphandlers.entities.ActionResponse;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ExtranetLoginAction {
    private static final String ACTION_LOGIN_FILE_NAME = "/bmw/rsp/executions/extranet_login.xml";
    private static final String ACTION_LOGIN_CONFIRMATION_FILE_NAME = "/bmw/rsp/executions/extranet_login_confirmation.xml";

    private Map<String, String> variables = new HashMap<>();

    public ExtranetLoginAction(Map<String, String> variables) {
        this.variables = variables;
    }

    public void execute(){
        ActionExecutor aeLogin = new ActionExecutor(ACTION_LOGIN_FILE_NAME, variables);
        ActionResponse arLogin = aeLogin.execute();

arLogin.getVariables();
        ActionExecutor aeLoginConfirmation = new ActionExecutor(ACTION_LOGIN_FILE_NAME, variables);
        ActionResponse arLoginConfirmation = aeLogin.execute();
    }
}
