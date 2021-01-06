package io.rk.nac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.rk.nac.model.Holding;
import io.rk.nac.model.Price;
import io.rk.nac.repo.HoldingsRepo;
import io.rk.nac.repo.PricingRepo;

@Service
public class NAVService {

    @Autowired
    HoldingsRepo holdingsRepo;
    @Autowired
    PricingRepo pricingRepo;

    public Map<String, Double> calculateNAV(String onDate) {
        List<Holding> holdings = holdingsRepo.getHoldingsByDate(onDate);
        List<Price> pricings = pricingRepo.getPricingsByDate(onDate);

        Map<String, Double> navs = new HashMap<String, Double>();

        Map<String, List<Holding>> hlMap = new HashMap<String, List<Holding>>();
        holdings.forEach(hl -> {
            if (hlMap.containsKey(hl.getFirmName())) {
                hlMap.get(hl.getFirmName()).add(hl);
            } else {
                List<Holding> hds = new ArrayList<Holding>();
                hds.add(hl);
                hlMap.put(hl.getFirmName(), hds);
            }
        });

        Map<String, Price> prMap = new HashMap<String, Price>();
        pricings.forEach(p -> {
            prMap.put(p.getFirmName(), p);
        });

        Map<String, Double> map = new HashMap<String, Double>();
        for (String str : hlMap.keySet()) {
            Price p = prMap.get(str);
            double pl = 0d;
            for (Holding hdl : hlMap.get(str)) {
                pl += hdl.getQuantity() * p.getMarketPrice();
            }
            map.put(str, pl);
        }
        return map;
    }
}
