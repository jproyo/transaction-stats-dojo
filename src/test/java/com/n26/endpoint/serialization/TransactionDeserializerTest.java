package com.n26.endpoint.serialization;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TransactionDeserializerTest {

    TransactionDeserializer target;

    @Before
    public void setup(){
        target = new TransactionDeserializer();
    }

    @Test
    public void deserializeOk() {
        try {
            String content = "{\"amount\":\"5\",\"timestamp\":\"2018-09-26T11:36:47.566Z\"}";
            JsonParser parser = new JsonFactory().createParser(content);
            parser.setCodec(new ObjectMapper());
            Transaction deserialize = target.deserialize(parser, null);
            assertEquals(new BigDecimal(5), deserialize.getAmount());
            assertEquals(new Long(1537961807566l), deserialize.getTimestamp());
        } catch (IOException e) {
            fail();
        }
    }

    @Test(expected = TransactionParseException.class)
    public void deserializeErrorAmount() {
        try {
            String content = "{\"amount\":\"hekfjk\",\"timestamp\":\"2018-09-26T11:36:47.566Z\"}";
            JsonParser parser = new JsonFactory().createParser(content);
            parser.setCodec(new ObjectMapper());
            Transaction deserialize = target.deserialize(parser, null);
            fail();
        } catch (IOException e) {
        }
    }

    @Test(expected = TransactionParseException.class)
    public void deserializeErrorDate() {
        try {
            String content = "{\"amount\":\"65\",\"timestamp\":\"2018-09-26\"}";
            JsonParser parser = new JsonFactory().createParser(content);
            parser.setCodec(new ObjectMapper());
            Transaction deserialize = target.deserialize(parser, null);
            fail();
        } catch (IOException e) {
        }
    }

    @Test(expected = HttpMessageNotReadableException.class)
    public void deserializeErrorWrongFormat() {
        try {
            String content = "\"Something wrong\"";
            JsonParser parser = new JsonFactory().createParser(content);
            parser.setCodec(new ObjectMapper());
            Transaction deserialize = target.deserialize(parser, null);
            fail();
        } catch (IOException e) {
        }
    }
}