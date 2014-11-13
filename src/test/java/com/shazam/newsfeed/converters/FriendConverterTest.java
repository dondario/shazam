package com.shazam.newsfeed.converters;

import com.shazam.newsfeed.EventType;
import com.shazam.newsfeed.TestHelper;
import com.shazam.newsfeed.model.FriendEvent;
import com.shazam.newsfeed.Platform;
import com.shazam.newsfeed.model.ProcessedEvent;
import com.shazam.newsfeed.model.ProcessedFriendEvent;
import com.shazam.newsfeed.validation.NewsFeedException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class FriendConverterTest {
    public static final Timestamp TIMESTAMP = new Timestamp(12345678L);
    private FriendConverter underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new FriendConverter();
    }

    @Test
    public void testCanConvert() throws Exception {
        assertTrue(underTest.canConvert(EventType.friend));
        assertFalse(underTest.canConvert(EventType.news));
    }

    @Test
    public void testConvertWithIos() throws Exception {
        Map<String, String> stores = new HashMap<>();
        stores.put("itunes", "itunes.url");
        stores.put("amazon", "amazon.url");
        FriendEvent friendEvent = TestHelper.createFriendEvent();

        ProcessedEvent actual = underTest.convert(friendEvent, Platform.ios);
        final ProcessedEvent expected = getProcessedFriendEvent("itunes.url");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testConvertWithAndroid() throws Exception {
        Map<String, String> stores = new HashMap<>();
        stores.put("itunes", "itunes.url");
        stores.put("amazon", "amazon.url");
        FriendEvent friendEvent = TestHelper.createFriendEvent();

        ProcessedEvent actual = underTest.convert(friendEvent, Platform.android);
        final ProcessedEvent expected = getProcessedFriendEvent("amazon.url");

        assertThat(actual, equalTo(expected));
    }

    @Test(expected = NewsFeedException.class)
    public void convertShouldThrowNewsFeedExceptionIfWrongType() throws Exception {
        underTest.convert(TestHelper.createNewsEvent(), Platform.android);
    }

    private ProcessedEvent getProcessedFriendEvent(String purchaseUrl) {
        return new ProcessedFriendEvent(TIMESTAMP,
                "from Shazamed 'title' by artist",
                "http://www.shazam.com/discover/track/trackId",
                purchaseUrl);
    }
}
