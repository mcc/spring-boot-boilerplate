package mcc.springbootboilerplate.utils;

import org.springframework.http.HttpStatus;

public class RestBody {
    Integer code;
    String message;

    public RestBody(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static RestBody of(HttpStatus status, String message){
        return new RestBody(status.value(), message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
