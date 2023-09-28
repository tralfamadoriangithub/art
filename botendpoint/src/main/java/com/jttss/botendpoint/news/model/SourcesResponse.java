package com.jttss.botendpoint.news.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SourcesResponse {

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "results")
    private List<Source> results;
}
