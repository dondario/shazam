package com.shazam.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawFeedContainer {
    List<RawEvent> newsFeed;

    @JsonCreator
    public RawFeedContainer(@JsonProperty("feed") final List<RawEvent> feed) {
        this.newsFeed = feed;
    }

    @JsonIgnore
    public List<RawEvent> getNewsFeed() {
        return newsFeed;
    }

}
