package com.shazam.newsfeed;

import com.shazam.newsfeed.model.FriendEvent;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;

public class FriendEventTest {
    private FriendEvent underTest;

    @Before
    public void setUp()  {
        Map<String, String> stores = new HashMap<>();
        stores.put("itunes", "itunes.url");
        stores.put("amazon", "amazon.url");
        underTest = new FriendEvent(new Timestamp(12345678L), "title", "event", stores, "from", "trackId", "artist");

    }

    @Test
    public void testGetEventType()  {
        assertThat(underTest.getEventType(), equalTo(EventType.friend));
    }

    @Test
    public void testGetPurchaseUrlForIos()  {
        assertThat(underTest.getPurchaseUrl(Platform.ios), equalTo("itunes.url"));
    }
        
    @Test
    public void testGetPurchaseUrlForAndroid()  {
        assertThat(underTest.getPurchaseUrl(Platform.android), equalTo("amazon.url"));
    }
    
    @Test
    public void testGetPurchaseUrlIfNullStoreInformation()  {
        underTest = new FriendEvent(new Timestamp(12345678L), "title", "event", null, "from", "trackId", "artist");
        assertNull(underTest.getPurchaseUrl(Platform.android));
    }

    @Test
    public void testGetPurchaseUrlWhenStoreDoesNotExist()  {
        underTest = new FriendEvent(new Timestamp(12345678L), "title", "event", new HashMap<String, String>(), "from", "trackId", "artist");

        assertNull(underTest.getPurchaseUrl(Platform.android));
    }
}
