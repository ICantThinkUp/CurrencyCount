package com.alfabank_test.demo.services;

import com.alfabank_test.demo.decoders.ResponseDecoder;
import com.alfabank_test.demo.interfases.CurrencyClient;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class CurrencyService {

    final CurrencyClient currencyClient;

    @Value("${currency.code}")
    private String currencyCode;

    @Autowired
    public CurrencyService(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    public boolean isIncreased() throws Exception {
        try {
            Double todayRate = getTodayExchangeRate(currencyCode);
            Double yesterdayRate = getYesterdayExchangeRate(currencyCode);
            return todayRate < yesterdayRate;
        } catch (Exception ex) {
            throw new Exception("Server is dead: " + ex.getMessage());
        }
    }

    private Double getYesterdayExchangeRate(String currencyCode) {
        String preparedDate = prepareYesterdayDate();
        Response rawCurrencyResponse = currencyClient.getYesterdayRate(preparedDate);
        JsonNode clearCurrencyResponseInThree = prepareCurrencyResponse(rawCurrencyResponse);
        return getExchangeRateCurrencyFromResponseThree(currencyCode, clearCurrencyResponseInThree);
    }

    private Double getTodayExchangeRate(String currencyCode) throws NullPointerException {
        Response rawCurrencyResponse = currencyClient.getLatestRate();
        JsonNode clearCurrencyResponseInThree = prepareCurrencyResponse(rawCurrencyResponse);
        assert clearCurrencyResponseInThree != null;

        return getExchangeRateCurrencyFromResponseThree(currencyCode, clearCurrencyResponseInThree);
    }

    private Double getExchangeRateCurrencyFromResponseThree(String code, JsonNode responseThree) throws NullPointerException {
        return responseThree.get("rates").get("RUB").asDouble();
    }

    private JsonNode prepareCurrencyResponse(Response rawCurrencyResponse) {
        try {
            Decoder decoderJsonToThree = new ResponseDecoder();

            // second arg must to dead
            JsonNode rootNodeOfThree = (JsonNode) decoderJsonToThree.decode(rawCurrencyResponse, GifService.class);

            return rootNodeOfThree;
        } catch (Exception ex) {
            System.out.println("Currency dont found");
            return null;
        }
    }

    private String prepareYesterdayDate() {
        Date yesterday = getYesterday();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(yesterday.getTime());
    }

    private Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

}
