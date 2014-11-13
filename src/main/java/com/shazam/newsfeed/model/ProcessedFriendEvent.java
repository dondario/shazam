package com.shazam.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProcessedFriendEvent implements Serializable, ProcessedEvent {

    private final Timestamp timestamp;
    private final String title;
    private final String shazamUrl;
    private final String purchaseUrl;

    public ProcessedFriendEvent(final Timestamp timestamp, final String title, final String shazamUrl, final String purchaseUrl) {

        this.timestamp = timestamp;
        this.title = title;
        this.shazamUrl = shazamUrl;
        this.purchaseUrl = purchaseUrl;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @JsonProperty("shazamurl")
    public String getShazamUrl() {
        return shazamUrl;
    }

    @JsonProperty("purchaseurl")
    @Override
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
        ProcessedFriendEvent rhs = (ProcessedFriendEvent) obj;
        return new EqualsBuilder()
                .append(this.timestamp, rhs.timestamp)
                .append(this.title, rhs.title)
                .append(this.shazamUrl, rhs.shazamUrl)
                .append(this.purchaseUrl, rhs.purchaseUrl)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(timestamp)
                .append(title)
                .append(shazamUrl)
                .append(purchaseUrl)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("timestamp", timestamp)
                .append("title", title)
                .append("shazamUrl", shazamUrl)
                .append("purchaseUrl", purchaseUrl)
                .toString();
    }
}
