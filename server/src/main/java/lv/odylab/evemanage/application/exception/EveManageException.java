package lv.odylab.evemanage.application.exception;

public class EveManageException extends Exception {
    private String argument;

    public EveManageException(String argument, String errorCode) {
        super(errorCode);

        this.argument = argument;
    }

    public EveManageException(String errorCode, Exception e) {
        super(errorCode, e);
    }

    public String getArgument() {
        return argument;
    }
}
