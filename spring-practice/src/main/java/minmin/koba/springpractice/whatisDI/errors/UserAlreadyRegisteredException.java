package minmin.koba.springpractice.whatisDI.errors;

public class UserAlreadyRegisteredException extends RuntimeException{

    public UserAlreadyRegisteredException() {
    }

    public UserAlreadyRegisteredException(String arg0) {
        super(arg0);
    }

    public UserAlreadyRegisteredException(Throwable arg0) {
        super(arg0);
    }

    public UserAlreadyRegisteredException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public UserAlreadyRegisteredException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}