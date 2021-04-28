package com.alfabank_test.demo.interfases;

import com.alfabank_test.demo.model.Currency;
import feign.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(contextId = "currency", value = "currency", url = "https://openexchangerates.org/api")
public interface CurrencyClient {

    @RequestMapping(method = RequestMethod.GET, value = "/latest.json?app_id=f8594cb32e84497eb88eac7a08070502", produces = "application/json")
    Response getLatestRate();

    @RequestMapping(method = RequestMethod.GET, value = "/historical/{dateInYMDWithDash}.json?app_id=f8594cb32e84497eb88eac7a08070502")
    Response getYesterdayRate(@PathVariable String dateInYMDWithDash);
}
