package com.kishor.Ecommerce.project.exceptions;

import com.kishor.Ecommerce.project.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String>response=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String filedName=((FieldError)err).getField();
            String mesage=err.getDefaultMessage();
            response.put(filedName,mesage);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RescourceNotFoundException.class)
    public ResponseEntity<ApiResponse>myResourceNotFoundException
            (RescourceNotFoundException e){
        String message= e.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse>myApiException
            (ApiException e){
        String message= e.getMessage();
        ApiResponse apiResponse= new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

}
