package com.shazam.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shazam.newsfeed.EventType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;
import java.util.Map;

public class FriendEvent extends RawEvent {

    private String from;
    private String trackId;
    private String artist;

    @JsonCreator
    public FriendEvent(@JsonProperty("timestamp") final Timestamp timestamp,
                       @JsonProperty("title") final String title,
                       @JsonProperty("event") final String event,
                       @JsonProperty("stores") final Map<String, String> stores,
                       @JsonProperty("from") final String from,
                       @JsonProperty("trackid") final String trackId,
                       @JsonProperty("artist") final String artist) {
        super(timestamp, title, event, stores);

        this.from = from;
        this.trackId = trackId;
        this.artist = artist;
    }

    public String getFrom() {
        return from;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public EventType getEventType() {
        return EventType.friend;
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
        FriendEvent rhs = (FriendEvent) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.from, rhs.from)
                .append(this.trackId, rhs.trackId)
                .append(this.artist, rhs.artist)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(from)
                .append(trackId)
                .append(artist)
                .toHashCode();
    }


}


