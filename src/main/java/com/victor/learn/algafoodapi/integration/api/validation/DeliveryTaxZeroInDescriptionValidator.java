package com.victor.learn.algafoodapi.integration.api.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class DeliveryTaxZeroInDescriptionValidator implements ConstraintValidator<DeliveryTaxFreeOnDescription, Object> {

    private String fieldValue;
    private String fieldDescription;
    private String requiredDescription;
    
    @Override
    public void initialize(DeliveryTaxFreeOnDescription constraintAnnotation) {
        this.fieldValue = constraintAnnotation.fieldValue();
        this.fieldDescription = constraintAnnotation.fieldDescription();
        this.requiredDescription = constraintAnnotation.requiredDescription();
    }

    @Override
    public boolean isValid(Object objectValidation, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        
        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), fieldValue)
                    .getReadMethod().invoke(objectValidation);
            
            String description = (String) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), fieldDescription)
                    .getReadMethod().invoke(objectValidation);
            
            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                valid = description.toLowerCase().contains(this.requiredDescription.toLowerCase());
            }
            
        } catch (Exception e) {
            throw new ValidationException(e);
        }
        return valid;
    }
}
