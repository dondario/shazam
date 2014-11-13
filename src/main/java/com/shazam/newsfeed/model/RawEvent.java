package com.shazam.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.shazam.newsfeed.EventType;
import com.shazam.newsfeed.Platform;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "event")
@JsonSubTypes({
                      @JsonSubTypes.Type(value = FriendEvent.class, name = "friend"),
                      @JsonSubTypes.Type(value = NewsEvent.class, name = "news")})
public abstract class RawEvent {

    private Timestamp timestamp;
    private String title;
    private String event;
    private final Map<String, String> stores;
    private static final Map<Platform, String> platformToStoreMap;
    static {
        Map<Platform, String> aMap = new HashMap<>();
        aMap.put(Platform.ios, "itunes");
        aMap.put(Platform.android, "amazon");

        platformToStoreMap = Collections.unmodifiableMap(aMap);
    }

    protected RawEvent(final Timestamp timestamp, final String title, final String event, final Map<String, String> stores) {
        this.timestamp = timestamp;
        this.title = title;
        this.event = event;
        this.stores = stores;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getEvent() {
        return event;
    }

    public abstract EventType getEventType();

    public Map<String, String> getStores() {
        return stores;
    }

    public String getPurchaseUrl(Platform platform) {
        if(stores == null) {
            return null;
        }
        return stores.get(platformToStoreMap.get(platform));
    };


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
        RawEvent rhs = (RawEvent) obj;
        return new EqualsBuilder()
                .append(this.timestamp, rhs.timestamp)
                .append(this.title, rhs.title)
                .append(this.event, rhs.event)
                .append(this.stores, rhs.stores)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(timestamp)
                .append(title)
                .append(event)
                .append(stores)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + timestamp +
                ", title='" + title + '\'' +
                ", event='" + event + '\'' +
                ", stores=" + stores +
                '}';
    }
}
