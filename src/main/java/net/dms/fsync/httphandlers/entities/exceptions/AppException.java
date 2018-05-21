package net.dms.fsync.httphandlers.entities.exceptions;

/**
 * Created by dminanos on 15/04/2017.
 */
public class AppException extends RuntimeException {

    public AppException(){
        super();
    }
    public AppException(Throwable ex){
        super(ex);
    }
    public AppException(String message){
        super(message);
    }
}
