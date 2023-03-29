package com.itblee;

import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.itblee.bean.ErrorResponseBean;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(FieldRequiredException.class)
    public ResponseEntity<ErrorResponseBean> handleFieldRequiredException(FieldRequiredException ex, WebRequest request) {
		ErrorResponseBean body = new ErrorResponseBean();
		body.setError(ex.getMessage());
        return new ResponseEntity<ErrorResponseBean>(body, HttpStatus.BAD_REQUEST);
    }
	
}
