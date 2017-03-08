package ru.bmstu.iu7.main;

public enum  ResponseCode {
    OK(0, "OK"),
    PARAMETER_MISSING(1, "Required parameter is missing"),
    AUTH_ERROR(2, "Authorization error"),
    DUPLICATE_ENTRY(3, "Duplicate entry"),
    ENTRY_NOT_FOUND(4, "Entry not found"),
    DB_ERROR(8, "Error querying database");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
