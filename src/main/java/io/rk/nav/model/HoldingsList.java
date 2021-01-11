package io.rk.nav.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class HoldingsList {
    private int totalRecords;
    private int recordsPerPage;
    private int page;
    private String nextPage;
    @JsonProperty("data")
    private List<Holding> data;
}
