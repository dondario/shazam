package com.shazam.newsfeed.converters;

import com.shazam.newsfeed.EventType;
import com.shazam.newsfeed.Platform;
import com.shazam.newsfeed.TestHelper;
import com.shazam.newsfeed.model.ProcessedEvent;
import com.shazam.newsfeed.model.ProcessedNewsEvent;
import com.shazam.newsfeed.validation.NewsFeedException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class NewsConverterTest {
    public static final Timestamp TIMESTAMP = new Timestamp(12345678L);


    private NewsConverter underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new NewsConverter();
    }

    @Test
    public void testCanConvert() throws Exception {
        assertTrue(underTest.canConvert(EventType.news));
        assertFalse(underTest.canConvert(EventType.friend));
    }

    @Test(expected = NewsFeedException.class)
    public void convertShouldThrowNewsFeedExceptionIfWrongType() throws Exception {
        underTest.convert(TestHelper.createFriendEvent(), Platform.android);
    }

    @Test
    public void convertShouldReturnCorrectProcessedNewsFeedForAndroid() throws NewsFeedException {
        ProcessedEvent actual = underTest.convert(TestHelper.createNewsEvent(), Platform.android);
        ProcessedEvent expected = new ProcessedNewsEvent(TIMESTAMP, "title", "body", "url", "amazon.url");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void convertShouldReturnCorrectProcessedNewsFeedForIos() throws NewsFeedException {
        ProcessedEvent actual = underTest.convert(TestHelper.createNewsEvent(), Platform.ios);
        ProcessedEvent expected = new ProcessedNewsEvent(TIMESTAMP, "title", "body", "url", "itunes.url");

        assertThat(actual, equalTo(expected));
    }
}
