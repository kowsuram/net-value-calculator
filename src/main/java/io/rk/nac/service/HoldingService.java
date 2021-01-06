package io.rk.nac.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.rk.nac.model.Holding;
import io.rk.nac.repo.HoldingsRepo;

@Service
public class HoldingService {

    @Autowired
    private HoldingsRepo holdingsRepo;

    public List<Holding> getHoldingsByFirm(String firmName, String date) {
        return holdingsRepo.getHoldings(firmName, date);
    }

    public String persistHoldings(Holding holding) {
        return holdingsRepo.save(holding) != null ? "Saved" : "Not Saved";
    }

    public Page<Holding> findHoldings(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return holdingsRepo.findAll(pageable);
    }

    public Map<String, List<Holding>> findHoldings() {
        Map<String, List<Holding>> hldMap = new HashMap<String, List<Holding>>();
        List<Holding> holdings = holdingsRepo.findAll();
        holdings.forEach(op -> {
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            String dateKey = df.format(op.getOnDate());
            if (hldMap.containsKey(dateKey)) {
                hldMap.get(dateKey).add(op);
            } else {
                List<Holding> hlds = new ArrayList<Holding>();
                hlds.add(op);
                hldMap.put(dateKey, hlds);
            }
        });
        return hldMap;
    }

}
