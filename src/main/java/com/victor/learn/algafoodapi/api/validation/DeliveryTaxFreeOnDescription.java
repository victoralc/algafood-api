package com.victor.learn.algafoodapi.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {DeliveryTaxZeroInDescriptionValidator.class})
public @interface DeliveryTaxFreeOnDescription {

    String message() default "invalid description";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldValue();

    String fieldDescription();

    String requiredDescription();

}
