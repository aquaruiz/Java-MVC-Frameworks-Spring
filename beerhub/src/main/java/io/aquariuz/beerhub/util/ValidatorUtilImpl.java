package io.aquariuz.beerhub.util;

import io.aquariuz.beerhub.util.factory.ValidatorUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidatorUtilImpl implements ValidatorUtil {
    public ValidatorUtilImpl() {
    }

    @Override
    public <O> Set<ConstraintViolation<O>> makeValidation(O obj) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<O>> violations = validator.validate(obj);
        return violations;
    }
}
