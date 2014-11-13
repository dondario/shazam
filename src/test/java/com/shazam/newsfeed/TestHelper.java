package com.shazam.newsfeed;


import com.shazam.newsfeed.model.*;

import java.sql.Timestamp;
import java.util.*;

public class TestHelper {

    public static final Timestamp TIMESTAMP = new Timestamp(12345678L);

    public static FriendEvent createFriendEvent() {
        Map<String, String> stores = new HashMap<>();
        stores.put("itunes", "itunes.url");
        stores.put("amazon", "amazon.url");
        return new FriendEvent(TIMESTAMP, "title", "event", stores, "from", "trackId", "artist");
    }

    public static NewsEvent createNewsEvent() {
        Map<String, String> stores = new HashMap<>();
        stores.put("itunes", "itunes.url");
        stores.put("amazon", "amazon.url");
        return new NewsEvent(TIMESTAMP, "title", "event", stores, "body", "url");
    }

    public static RawFeedContainer createNewsFeedContainer() {

        List<RawEvent> rawEvents = new ArrayList<>();
        rawEvents.add(createFriendEvent());
        return new RawFeedContainer(rawEvents);
    }

    public static ProcessedFeedContainer createEventFeedContainer() {

        List<ProcessedEvent> events = new ArrayList<>();
        events.add(createProcessedFriendEvent());
        return new ProcessedFeedContainer(events);
    }

    public static ProcessedFriendEvent createProcessedFriendEvent() {
            return new ProcessedFriendEvent(TIMESTAMP,
                    "from Shazamed 'title' by artist",
                    "http://www.shazam.com/discover/track/trackId",
                    "purchaseUrl");
    }


}
