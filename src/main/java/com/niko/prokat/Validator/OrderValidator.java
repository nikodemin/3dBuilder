package com.niko.prokat.Validator;

import com.niko.prokat.Model.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpSession;

@Component
public class OrderValidator implements Validator {
    @Autowired
    HttpSession session;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == OrderDto.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderDto form = (OrderDto) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.orderForm.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fordays", "NotEmpty.orderForm.fordays");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "NotEmpty.orderForm.date");

        if (session.getAttribute("order") == null ||
                ((OrderDto) session.getAttribute("order")).getTools().size() == 0)
            errors.reject("NotEmpty.orderForm.tools");

    }
}
