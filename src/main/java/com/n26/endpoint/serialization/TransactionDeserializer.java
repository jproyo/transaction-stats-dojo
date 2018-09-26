package com.n26.endpoint.serialization;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.n26.core.util.FormatterUtil;
import com.n26.model.Transaction;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Deserialization Transaction Class to map between requested string to
 * typed structure {@link Transaction} class
 */
public class TransactionDeserializer extends StdDeserializer<Transaction> {

    /**
     * Instantiates a new Transaction deserializer.
     *
     * @param vc the vc
     */
    public TransactionDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Instantiates a new Transaction deserializer.
     */
    public TransactionDeserializer() {
        this(null);
    }

    @Override
    public Transaction deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Transaction.TransactionBuilder toStore = Transaction.create();
        try{
            JsonNode amount = node.get("amount");
            if(amount == null) throw new HttpMessageNotReadableException("Bad format json message");
            toStore = toStore.amount(new BigDecimal(amount.asText()));
        } catch (NumberFormatException e){
            throw new TransactionParseException("Error parsing amount");
        }
        try{
            JsonNode timestampNode = node.get("timestamp");
            if(timestampNode == null) throw new HttpMessageNotReadableException("Bad format json message");
            toStore = toStore.timestamp(FormatterUtil.parse(timestampNode.asText()));
        }catch (Exception e){
            throw new TransactionParseException("Error parsing date");
        }
        return toStore.build();
    }
}
