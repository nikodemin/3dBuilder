package com.niko.prokat.ExceptionHandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(basePackages = "com.niko.prokat.Controller.rest")
public class GlobalDefaultRestExceptionHandler {
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<?> handle(Exception e) throws Exception{
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
