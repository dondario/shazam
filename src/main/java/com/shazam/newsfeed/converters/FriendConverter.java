package com.shazam.newsfeed.converters;

import com.shazam.newsfeed.*;
import com.shazam.newsfeed.model.ProcessedEvent;
import com.shazam.newsfeed.model.RawEvent;
import com.shazam.newsfeed.model.FriendEvent;
import com.shazam.newsfeed.model.ProcessedFriendEvent;
import com.shazam.newsfeed.validation.NewsFeedException;

import static java.lang.String.format;

public class FriendConverter implements Converter {
    private static final String FROM_SHAZAMED_SONG_BY_ARTIST = "%s Shazamed '%s' by %s";
    private static final String SHAZAM_COM_DISCOVER_TRACK_ID = "http://www.shazam.com/discover/track/%s";

    public FriendConverter() {
    }

    @Override
    public boolean canConvert(EventType eventType) {
        return eventType == EventType.friend;
    }

    @Override
    public ProcessedEvent convert(RawEvent rawEvent, Platform platform) throws NewsFeedException {
        validateEventType(rawEvent);

        FriendEvent friendEvent = (FriendEvent) rawEvent;
        return new ProcessedFriendEvent(friendEvent.getTimestamp(),
                getTitle(friendEvent.getFrom(),
                        friendEvent.getTitle(),
                        friendEvent.getArtist()),
                getShazamUrl(friendEvent.getTrackId()),
                friendEvent.getPurchaseUrl(platform));

    }

    private void validateEventType(RawEvent rawEvent) throws NewsFeedException {
        if(!canConvert(rawEvent.getEventType())) {
            throw new NewsFeedException(format("%s can not be converted by the NewsConverter", rawEvent.getEventType().name()));
        }
    }

    private String getTitle(String from, String title, String artist) {
        return format(FROM_SHAZAMED_SONG_BY_ARTIST, from, title, artist);
    }

    private String getShazamUrl(String trackId) {
        return format(SHAZAM_COM_DISCOVER_TRACK_ID, trackId);
    }
}
