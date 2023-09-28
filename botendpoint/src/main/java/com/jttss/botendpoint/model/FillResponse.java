package com.jttss.botendpoint.model;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FillResponse implements Serializable {

    String channel;
    String source;
    int postCount;
    String query;
    List<String> links;
}
