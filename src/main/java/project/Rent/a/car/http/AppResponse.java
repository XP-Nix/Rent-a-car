package project.Rent.a.car.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class AppResponse {
    //general response, should work for all requests

    private static HashMap<String,Object> response = new HashMap<>();


    public static AppResponse success(){
        response = new HashMap<>();
        response.put("status","success");
        response.put("code", HttpStatus.OK.value());
        return new AppResponse();
    }
    public static AppResponse error(){
        response = new HashMap<>();
        response.put("status","error");
        response.put("code", HttpStatus.BAD_REQUEST.value());
        return new AppResponse();
    }



    //status codde
    public  AppResponse withCodde(HttpStatus code){
        response.put("code",code.value());
        return this;
    }
    //message
    public AppResponse withMessage(String message){
        response.put("message",message);
        return this;

    }

    public AppResponse withData(Object data){
        response.put("ddata",data);
        return this;

    }


    public ResponseEntity<Object> build(){
        int code = (int) response.get("code");
        return new ResponseEntity<Object>(response, HttpStatusCode.valueOf(code));
    }

}
