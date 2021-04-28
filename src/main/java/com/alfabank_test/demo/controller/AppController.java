package com.alfabank_test.demo.controller;

import com.alfabank_test.demo.services.CurrencyService;
import com.alfabank_test.demo.services.GifService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    private final CurrencyService currencySercive;
    private final GifService gifService;

    @Autowired
    public AppController(CurrencyService currencySercive, GifService gifService) {
        this.currencySercive = currencySercive;
        this.gifService = gifService;
    }

    @GetMapping("/")
    public ResponseEntity<? extends Object> main(Model model) {
        String gifSrc;
        try {
            gifSrc = (currencySercive.isIncreased()) ?
                    gifService.getRichGifSrc() :
                    gifService.getBrokeGifSrc();
            return ResponseEntity.status(200).body(gifSrc);
        } catch (Error | Exception err) {
            return ResponseEntity.status(500).build();
        }
    }
}
