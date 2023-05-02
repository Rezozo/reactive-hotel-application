package com.hotel.app.validate.impl;

import com.hotel.app.service.CustomerService;
import com.hotel.app.validate.EmailValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {
    @Autowired
    private CustomerService customerService;

    @SneakyThrows
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email.contains("@")) {
            Boolean res = customerService.existEmail(email).toFuture().get();
            return !res;
        }
        return false;
    }
}
