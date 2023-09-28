package com.jttss.botendpoint.news.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsResponse implements Serializable {

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "totalResults")
    private Integer totalResults;

    @JsonProperty(value = "nextPage")
    private String nextPage;

    @JsonProperty(value = "results")
    private List<News> results;
}
