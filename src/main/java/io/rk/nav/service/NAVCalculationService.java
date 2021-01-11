package io.rk.nav.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.rk.nav.model.Holding;
import io.rk.nav.model.HoldingsList;
import io.rk.nav.model.PriceList;
import io.rk.nav.model.Price;

@Service
public class NAVCalculationService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${nav.holding.url}")
	private String holdingURL;
	@Value("${nav.pricing.url}")
	private String pricingURL;
	

	public Map<String, Double> calculateNavLogic(String date) {
		
		Map<String, Double> navMap = new HashMap<>();
		Map<String, Map<String, List<Holding>>> holdingMap = new HashMap<String, Map<String, List<Holding>>>();
		Map<String, Map<String, Price>> pricingMap = new HashMap<String, Map<String, Price>>();
		
		try {
			ResponseEntity<Holding[]> hldResponse = restTemplate.getForEntity(holdingURL, Holding[].class);
			ResponseEntity<Price[]> prcResponse = restTemplate.getForEntity(pricingURL, Price[].class);

			if (hldResponse.getStatusCode().equals(HttpStatus.OK)
					&& prcResponse.getStatusCode().equals(HttpStatus.OK)) {
				Holding[] holdingsList = hldResponse.getBody();
				Price[] priceList = prcResponse.getBody();

				Arrays.asList(holdingsList).forEach(hold -> {
					if (holdingMap.containsKey(hold.getDate())) {
						Map<String, List<Holding>> resMap = holdingMap.get(hold.getDate());
						if (resMap.containsKey(hold.getSecurity())) {
							resMap.get(hold.getSecurity()).add(hold);
							holdingMap.put(hold.getDate(), resMap);
						} else {
							Map<String, List<Holding>> map = new HashMap<String, List<Holding>>();
							List<Holding> holdings = new ArrayList<Holding>();
							holdings.add(hold);
							map.put(hold.getSecurity(), holdings);
							resMap.putAll(map);
							holdingMap.put(hold.getDate(), resMap);
						}
					} else {
						Map<String, List<Holding>> map = new HashMap<String, List<Holding>>();
						List<Holding> holdings = new ArrayList<Holding>();
						holdings.add(hold);
						map.put(hold.getSecurity(), holdings);
						holdingMap.put(hold.getDate(), map);
					}
				});

				Arrays.asList(priceList).forEach(price -> {
					if (pricingMap.containsKey(price.getDate())) {
						Map<String, Price> resMap = pricingMap.get(price.getDate());
						resMap.put(price.getSecurity(), price);
						pricingMap.put(price.getDate(), resMap);
					} else {
						Map<String, Price> resMap = new HashMap<String, Price>();
						resMap.put(price.getSecurity(), price);
						pricingMap.put(price.getDate(), resMap);
					}
				});

			}
		} catch (Exception e) {
			throw e;
		}

		pricingMap.get(date).forEach((pk, pv) -> {
			Map<String, List<Holding>> firmVsHoldings = holdingMap.get(date);
			if(firmVsHoldings.containsKey(pk)) {
				double sum = firmVsHoldings.get(pk).stream().mapToDouble(o -> o.getQuantity() * pv.getPrice()).sum();
				navMap.put(pk, sum);
			}
		});
		return navMap;
	}

}
