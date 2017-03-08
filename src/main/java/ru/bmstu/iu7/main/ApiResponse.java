package ru.bmstu.iu7.main;

import org.springframework.http.ResponseEntity;

public class ApiResponse {
    private int code;
    private Object content;

    public ApiResponse() {
    }

    public ApiResponse(int code, Object content) {
        this.code = code;
        this.content = content;
    }

    public ApiResponse(ResponseCode responseCode) {
        this(responseCode.getCode(), responseCode.getMessage());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static ResponseEntity ok() {
        return ResponseEntity.ok(new ApiResponse(ResponseCode.OK));
    }

    public static ResponseEntity ok(Object content) {
        return ResponseEntity.ok(new ApiResponse(ResponseCode.OK.getCode(), content));
    }

    public static ResponseEntity parameterMissing() {
        return ResponseEntity.ok(new ApiResponse(ResponseCode.PARAMETER_MISSING));
    }

    public static ResponseEntity authError() {
        return ResponseEntity.ok(new ApiResponse(ResponseCode.AUTH_ERROR));
    }

    public static ResponseEntity duplicateEntry() {
        return ResponseEntity.ok(new ApiResponse(ResponseCode.DUPLICATE_ENTRY));
    }

    public static ResponseEntity entryNotFound() {
        return ResponseEntity.ok(new ApiResponse(ResponseCode.ENTRY_NOT_FOUND));
    }

    public static ResponseEntity dbError() {
        return ResponseEntity.ok(new ApiResponse(ResponseCode.DB_ERROR));
    }
}
