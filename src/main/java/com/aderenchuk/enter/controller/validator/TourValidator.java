package com.aderenchuk.enter.controller.validator;


import com.aderenchuk.enter.entity.Tour;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.aderenchuk.enter.constants.TourConstants.DIRECTION_MAX_SIZE;


@Component
public class TourValidator implements Validator  {

    @Override
    public boolean supports(Class<?> clazz) {
        return Tour.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "direction", "direction.empty");
        Tour tour = (Tour) target;

        if (StringUtils.hasLength(tour.getDirection())
                && tour.getDirection().length() > DIRECTION_MAX_SIZE) {
            errors.rejectValue("direction", "direction.maxSize");
        }
    }
}