package io.rk.nav.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Price {
	@JsonProperty("security")
    private String security;
	@JsonProperty("price")
    private double price;
	@JsonProperty("date")
    private String date;
}
