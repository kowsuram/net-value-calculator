package io.rk.nav.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Holding {
	@JsonProperty("security")
    private String security;
	@JsonProperty("portfolio")
    private String portfolio;
	@JsonProperty("date")
    private String date;
    @JsonProperty("quantity")
    private int quantity;
}
