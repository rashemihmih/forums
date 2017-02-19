package ru.bmstu.iu7.main;

import org.springframework.http.ResponseEntity;

public class Response {
    private int code;
    private Object content;

    public Response() {
    }

    public Response(int code, Object content) {
        this.code = code;
        this.content = content;
    }

    public Response(ResponseCode responseCode) {
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

    public static ResponseEntity ok(Object content) {
        return ResponseEntity.ok(new Response(ResponseCode.OK.getCode(), content));
    }

    public static ResponseEntity parameterMissing() {
        return ResponseEntity.ok(new Response(ResponseCode.PARAMETER_MISSING));
    }

    public static ResponseEntity authError() {
        return ResponseEntity.ok(new Response(ResponseCode.AUTH_ERROR));
    }

    public static ResponseEntity duplicateUser() {
        return ResponseEntity.ok(new Response(ResponseCode.DUPLICATE_USER));
    }

    public static ResponseEntity dbError() {
        return ResponseEntity.ok(new Response(ResponseCode.DB_ERROR));
    }
}
