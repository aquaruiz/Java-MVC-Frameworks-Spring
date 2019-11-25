package io.aquariuz.beerhub.util.factory;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatorUtil {
    <O> Set<ConstraintViolation<O>> makeValidation(O obj);
}
