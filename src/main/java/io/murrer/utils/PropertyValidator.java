package io.murrer.utils;


import io.murrer.mojo.AbstractMojoProperties;
import org.apache.maven.plugin.logging.Log;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class PropertyValidator {

    public static <T extends AbstractMojoProperties> boolean hasErrors(String propertyType, Log log, T properties) {
                ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory();

        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> validationResult = validator.validate(properties);

        for (ConstraintViolation<Object> violation : validationResult) {
            log.error(violation.getMessage());
        }

        return validationResult.size() > 0;
    }
}
