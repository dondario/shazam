package com.shazam.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shazam.newsfeed.EventType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;
import java.util.Map;

public class NewsEvent extends RawEvent {

    private String body;
    private String url;


    @JsonCreator
    public NewsEvent(@JsonProperty("timestamp") final Timestamp timestamp,
                     @JsonProperty("title") final String title,
                     @JsonProperty("event") final String event,
                     @JsonProperty("stores") final Map<String, String> stores,
                     @JsonProperty("body") final String body,
                     @JsonProperty("url") final String url) {

        super(timestamp, title, event, stores);
        this.body = body;
        this.url = url;
    }


    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public EventType getEventType() {
        return EventType.news;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        NewsEvent rhs = (NewsEvent) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.body, rhs.body)
                .append(this.url, rhs.url)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(body)
                .append(url)
                .toHashCode();
    }
}


