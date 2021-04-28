package com.alfabank_test.demo.interfases;

import com.alfabank_test.demo.config.AppConfig;
import feign.RequestLine;
import feign.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId = "gif", value = "gifValue", url = "api.giphy.com",
configuration = AppConfig.class)
public interface GifClient {
    @GetMapping("/v1/gifs/random?api_key=0mDCxkhl83ClD6ABz0QdQYhvQpJmXUSD&limit=1&tag=rich")
    Response getRichGif();
    @GetMapping("/v1/gifs/random?api_key=0mDCxkhl83ClD6ABz0QdQYhvQpJmXUSD&limit=1&tag=broke")
    Response getBrokeGif();
}
