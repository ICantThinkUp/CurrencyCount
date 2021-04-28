package com.alfabank_test.demo.services;

import com.alfabank_test.demo.decoders.ResponseDecoder;
import com.alfabank_test.demo.interfases.GifClient;
import com.alfabank_test.demo.responses.GifBodyResponse;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@AllArgsConstructor
public class GifService {

    @Autowired
    GifClient gifClient;

    public String getRichGifSrc() {
        Response rawResponse = gifClient.getRichGif();
        return decodeResponseAndGetSrc(rawResponse);
    }

    public String getBrokeGifSrc() {
        Response rawResponse = gifClient.getBrokeGif();
        return decodeResponseAndGetSrc(rawResponse);
    }

    private String decodeResponseAndGetSrc(Response rawResponse) {
        try {
            ResponseDecoder decoderJsonToThree = new ResponseDecoder();
            JsonNode rootNodeOfThree = decoderJsonToThree.decode(rawResponse, GifBodyResponse.class);
            return getSrcFromThree(rootNodeOfThree);
        } catch (Exception ex) {
            rawResponse.close();
            return "Not implemented";
        }
    }

    private String getSrcFromThree(JsonNode rootNode) {
        JsonNode dataNode = rootNode.get("data");
        JsonNode imagesNode = dataNode.get("images");
        JsonNode originalNode = imagesNode.get("original");
        String url = originalNode.get("url").toString().replaceAll("^\"|\"$", "");
        return url;
    }

}
