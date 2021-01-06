package io.rk.nac.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.rk.nac.model.Holding;
import io.rk.nac.model.Price;
import io.rk.nac.service.HoldingService;
import io.rk.nac.service.NAVService;
import io.rk.nac.service.PricingService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/arcjsonapi/HoldingValueCalculator/master")
public class NAVController {

    @Autowired
    private HoldingService holdingService;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private NAVService navService;

    @PostMapping(value = "/api/holding/save", consumes = "application/json")
    public String saveHoldings(@RequestBody Holding holding) {
        return holdingService.persistHoldings(holding);
    }

    @PostMapping(value = "/api/pricing/save", consumes = "application/json")
    public String savePricing(@RequestBody Price price) {
        return pricingService.persistPricing(price);
    }

    @GetMapping(value = "/paging/holding_start")
    public Page<Holding> findHoldings(@RequestParam("page") int page, @RequestParam("size") int size) {
        return holdingService.findHoldings(page, size);
    }

    @GetMapping(value = "/paging/pricing_start")
    public Page<Price> findPricings(@RequestParam("page") int page, @RequestParam("size") int size) {
        return pricingService.findPricings(page, size);
    }

    @GetMapping(value = "/api/holding")
    public Map<String, List<Holding>> viewHoldingDtlsOnEachDay() {
        return holdingService.findHoldings();
    }

    @GetMapping(value = "/api/pricing")
    public Map<String, List<Price>> viewPricingDtlsOnEachDay() {
        return pricingService.findPricings();
    }

    @GetMapping(value = "/calculateNAV")
    public Map<String, Double> calculateNAV(@RequestParam("onDate") String onDate) {
        return navService.calculateNAV(onDate);
    }

}
