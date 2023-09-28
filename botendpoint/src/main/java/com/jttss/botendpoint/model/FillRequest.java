package com.jttss.botendpoint.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FillRequest implements Serializable {

    private String channel;
    private String source;
    private int postCount;
    private String query;

}
