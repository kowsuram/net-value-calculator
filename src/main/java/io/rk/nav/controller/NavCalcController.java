package io.rk.nav.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.rk.nav.service.NAVCalculationService;

@RestController
public class NavCalcController {

    @Autowired
    private NAVCalculationService calcService;

    @GetMapping(value="/calculate")
    public Map<String, Double> calculateNav(@RequestParam("date") String date) {
        return calcService.calculateNavLogic(date);
    }

}
