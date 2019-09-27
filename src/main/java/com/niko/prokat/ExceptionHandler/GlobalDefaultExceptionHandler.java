package com.niko.prokat.ExceptionHandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice(basePackages = {"com.niko.prokat.Controller.usual",
        "com.niko.prokat.Service"})
public class GlobalDefaultExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public String handle(HttpServletRequest req, Exception e,
                         Model model) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(),
                ResponseStatus.class) != null)
            throw e;

        log.error(e.getMessage(),e);
        model.addAttribute("text","500 - Внутренняя ошибка сервера");
        return "text";
    }
}
