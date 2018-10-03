package edu.jproyo.service.validator;

/**
 * The interface Validator.
 */
public interface Validator<T> {

    /**
     * Validate validator result.
     *
     * @param  validatable object
     * @return the validator result
     */
    ValidatorResult validate(T validatable);

}
