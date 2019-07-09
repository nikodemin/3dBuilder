package com.dBuider.app.Validator;

import com.dBuider.app.Model.Form.OrderForm;
import com.dBuider.app.Model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpSession;

@Component
public class OrderValidator implements Validator
{
    @Autowired
    HttpSession session;

    @Override
    public boolean supports(Class<?> aClass)
    {
        return aClass == OrderForm.class;
    }

    @Override
    public void validate(Object o, Errors errors)
    {
        OrderForm form = (OrderForm)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.orderForm.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fordays", "NotEmpty.orderForm.fordays");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "NotEmpty.orderForm.date");

        if (session.getAttribute("order") == null ||
                ((Order)session.getAttribute("order")).getTools().size() == 0)
            errors.rejectValue("tools","NotEmpty.orderForm.tools");

    }
}
