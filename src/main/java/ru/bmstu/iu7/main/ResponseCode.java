package ru.bmstu.iu7.main;

public enum  ResponseCode {
    OK(0, "OK"),
    PARAMETER_MISSING(1, "В запросе недостаточно параметров"),
    AUTH_ERROR(2, "Ошибка авторизации"),
    DUPLICATE_ENTRY(3, "Запись уже существует"),
    ENTRY_NOT_FOUND(4, "Запись не найдена"),
    DB_ERROR(8, "Ошибка при работе с базой данных");

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
