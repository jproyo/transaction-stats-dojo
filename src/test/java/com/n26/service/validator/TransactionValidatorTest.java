package com.n26.service.validator;

import com.n26.config.Config;
import com.n26.model.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionValidatorTest {

    private Validator<Transaction> target;

    @Before
    public void setup(){
        target = new TransactionValidator();
        ((TransactionValidator)target).setConfig(new Config());
    }

    @Test
    public void validate() {
        Transaction tx = Transaction.create()
                .timestamp(System.currentTimeMillis()-1000)
                .amount(new BigDecimal(5))
                .build();
        ValidatorResult validate = target.validate(tx);
        assertNotNull(validate);
        assertEquals(ValidatorResult.VALID, validate);
    }

    @Test
    public void validateInvalid() {
        Transaction tx = null;
        ValidatorResult validate = target.validate(tx);
        assertNotNull(validate);
        assertEquals(ValidatorResult.INVALID, validate);

        ValidatorResult validate2 = target.validate(Transaction.create().build());
        assertNotNull(validate2);
        assertEquals(ValidatorResult.INVALID, validate2);
    }

    @Test
    public void validateOld() {
        Transaction tx = Transaction.create()
                .timestamp(System.currentTimeMillis()-61000)
                .amount(new BigDecimal(5))
                .build();
        ValidatorResult validate = target.validate(tx);
        assertNotNull(validate);
        assertEquals(ValidatorResult.OLD, validate);
    }

    @Test
    public void validateFuture() {
        Transaction tx = Transaction.create()
                .timestamp(System.currentTimeMillis()+1000)
                .amount(new BigDecimal(5))
                .build();
        ValidatorResult validate = target.validate(tx);
        assertNotNull(validate);
        assertEquals(ValidatorResult.FUTURE, validate);
    }


}