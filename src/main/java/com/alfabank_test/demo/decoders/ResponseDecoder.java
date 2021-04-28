package com.alfabank_test.demo.decoders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;

public class ResponseDecoder implements Decoder {
    private final ObjectMapper responseBodyDecoder;

    public ResponseDecoder() {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.responseBodyDecoder = mapper;
    }

    @Override
    public JsonNode decode(Response response, Type type)
            throws IOException, DecodeException, FeignException {
        JsonNode body;
        try {
            body = this.responseBodyDecoder.readTree(response.body().toString());
        } catch (Exception ex) {
            System.out.println("err " + ex.getMessage());
            return null;
        }
        return body;
    }
}
