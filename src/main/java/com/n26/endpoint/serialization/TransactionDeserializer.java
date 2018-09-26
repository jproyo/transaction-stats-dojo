package com.n26.endpoint.serialization;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.n26.core.util.FormatterUtil;
import com.n26.model.Transaction;

import java.io.IOException;
import java.math.BigDecimal;

public class TransactionDeserializer extends StdDeserializer<Transaction> {

    public TransactionDeserializer(Class<?> vc) {
        super(vc);
    }

    public TransactionDeserializer() {
        this(null);
    }

    @Override
    public Transaction deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node =  jp.getCodec().readTree(jp);
        Transaction.TransactionBuilder toStore = Transaction.create();
        try{
            JsonNode amount = node.get("amount");
            if(amount != null){
                toStore = toStore.amount(new BigDecimal(amount.asText()));
            }
        } catch (NumberFormatException e){
            throw new TransactionParseException("Error parsing amount");
        }
        try{
            JsonNode timestampNode = node.get("timestamp");
            if(timestampNode != null){
                toStore = toStore.timestamp(FormatterUtil.parse(timestampNode.asText()));
            }
        }catch (Exception e){
            throw new TransactionParseException("Error parsing date");
        }
        return toStore.build();
    }
}
