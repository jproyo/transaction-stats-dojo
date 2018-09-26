package com.n26.endpoint.serialization;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.n26.model.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TransactionDeserializer extends StdDeserializer<Transaction> {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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
                String timestamp = timestampNode.asText();
                LocalDateTime deserialised = null;
                if (timestamp.length() > 10 && timestamp.charAt(10) == 'T') {
                    deserialised = timestamp.endsWith("Z") ? LocalDateTime.ofInstant(Instant.parse(timestamp), ZoneOffset.UTC) : LocalDateTime.parse(timestamp, DEFAULT_FORMATTER);
                } else {
                    deserialised = LocalDateTime.parse(timestamp, DEFAULT_FORMATTER);
                }
                toStore = toStore.timestamp(deserialised.toInstant(ZoneOffset.UTC).toEpochMilli());
            }
        }catch (Exception e){
            throw new TransactionParseException("Error parsing date");
        }
        return toStore.build();
    }
}
