package lv.odylab.evemanage.application.exception;

public class CsvImportException extends RuntimeException {
    public CsvImportException(String message) {
        super(message);
    }
}