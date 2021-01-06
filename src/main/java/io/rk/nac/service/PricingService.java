package io.rk.nac.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.rk.nac.model.Price;
import io.rk.nac.repo.PricingRepo;

@Service
public class PricingService {

    @Autowired
    private PricingRepo pricingRepo;

    public List<Price> getPricingsByFirmName(String firmName, String date) {
        return pricingRepo.getHoldings(firmName, date);
    }

    public String persistPricing(Price price) {
        return pricingRepo.save(price) != null ? "Saved" : "Not Saved";
    }

    public Page<Price> findPricings(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return pricingRepo.findAll(pageRequest);
    }

    public Map<String, List<Price>> findPricings() {
        Map<String, List<Price>> prMap = new HashMap<String, List<Price>>();
        List<Price> pricings = pricingRepo.findAll();
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        pricings.forEach(op -> {
            String dateKey = df.format(op.getOnDate());
            if (prMap.containsKey(dateKey)) {
                prMap.get(dateKey).add(op);
            } else {
                List<Price> prs = new ArrayList<Price>();
                prs.add(op);
                prMap.put(dateKey, prs);
            }
        });
        return prMap;
    }

}
