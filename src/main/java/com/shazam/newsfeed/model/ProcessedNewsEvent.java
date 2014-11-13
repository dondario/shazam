package com.shazam.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;

public class ProcessedNewsEvent implements ProcessedEvent {
    private Timestamp timestamp;
    private String title;
    private String body;
    private String storyUrl;
    private String purchaseUrl;

    public ProcessedNewsEvent(Timestamp timestamp, String title, String body, String storyUrl, String purchaseUrl) {
        this.timestamp = timestamp;
        this.title = title;
        this.body = body;
        this.storyUrl = storyUrl;
        this.purchaseUrl = purchaseUrl;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @JsonProperty("storyurl")
    public String getStoryUrl() {
        return storyUrl;
    }

    @JsonProperty("purchaseurl")
    public String getPurchaseUrl() {
        return purchaseUrl;
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
        ProcessedNewsEvent rhs = (ProcessedNewsEvent) obj;
        return new EqualsBuilder()
                .append(this.timestamp, rhs.timestamp)
                .append(this.title, rhs.title)
                .append(this.body, rhs.body)
                .append(this.storyUrl, rhs.storyUrl)
                .append(this.purchaseUrl, rhs.purchaseUrl)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(timestamp)
                .append(title)
                .append(body)
                .append(storyUrl)
                .append(purchaseUrl)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ProcessedNewsEvent{" +
                "timestamp=" + timestamp +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", storyUrl='" + storyUrl + '\'' +
                ", purchaseUrl='" + purchaseUrl + '\'' +
                '}';
    }
}
