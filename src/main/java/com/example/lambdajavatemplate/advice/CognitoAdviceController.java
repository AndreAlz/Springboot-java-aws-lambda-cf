package com.example.lambdajavatemplate.advice;

import com.amazonaws.services.cognitoidp.model.ExpiredCodeException;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.example.lambdajavatemplate.exceptions.BaseExceptionResponse;
import com.example.lambdajavatemplate.exceptions.CognitoErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CognitoAdviceController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<BaseExceptionResponse> handlerNoAuthorized(RuntimeException ex){
        return new ResponseEntity<>(new BaseExceptionResponse(CognitoErrorCodes.USER_PASSWORD_ERROR.toString(), "El usuario o contraseña son incorrectos."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredCodeException.class)
    public ResponseEntity<BaseExceptionResponse> handlerExpiredCodeException(RuntimeException ex){
        return new ResponseEntity<>(new BaseExceptionResponse(CognitoErrorCodes.VERIFICATION_CODE_INCORRECT.toString(), "El codigo enviado ha expirado o es incorrecto."), HttpStatus.BAD_REQUEST);
    }
}
