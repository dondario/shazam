package com.shazam.newsfeed.model;

import com.shazam.newsfeed.EventType;
import com.shazam.newsfeed.Platform;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;

public class NewsEventTest {
    private NewsEvent underTest;

    @Before
    public void setUp()  {
        Map<String, String> stores = new HashMap<>();
        stores.put("itunes", "itunes.url");
        stores.put("amazon", "amazon.url");
        underTest = new NewsEvent(new Timestamp(12345678L), "title", "event", stores, "body", "url");

    }

    @Test
    public void testGetEventType()  {
        assertThat(underTest.getEventType(), equalTo(EventType.news));
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
        underTest = new NewsEvent(new Timestamp(12345678L), "title", "event", null, "body", "url");
        assertNull(underTest.getPurchaseUrl(Platform.android));
    }

    @Test
    public void testGetPurchaseUrlWhenStoreDoesNotExist()  {
        underTest = new NewsEvent(new Timestamp(12345678L), "title", "event", new HashMap<String, String>(), "body", "url");

        assertNull(underTest.getPurchaseUrl(Platform.android));
    }
}
