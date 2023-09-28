package com.jttss.botendpoint.news.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class News implements Serializable {

    @JsonProperty(value = "article_id")
    private String articleId;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "keywords")
    private List<String> keywords;

    @JsonProperty(value = "creator")
    private List<String> creator;

    @JsonProperty(value = "video_url")
    private String videoUrl;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "content")
    private String content;

    @JsonProperty(value = "pubDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pubDate;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty(value = "source_id")
    private String soutceId;

    @JsonProperty(value = "source_priority")
    private Integer sourcePriority;

    @JsonProperty(value = "country")
    private List<String> country;

    @JsonProperty(value = "category")
    private List<String> category;

    @JsonProperty(value = "language")
    private String language;
}
