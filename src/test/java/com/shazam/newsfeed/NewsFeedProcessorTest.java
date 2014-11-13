package com.shazam.newsfeed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.shazam.newsfeed.converters.Converter;
import com.shazam.newsfeed.model.ProcessedFeedContainer;
import com.shazam.newsfeed.model.ProcessedFriendEvent;
import com.shazam.newsfeed.model.RawEvent;
import com.shazam.newsfeed.model.RawFeedContainer;
import com.shazam.newsfeed.validation.NewsFeedException;
import com.shazam.newsfeed.validation.NewsFeedValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NewsFeedProcessorTest {
    public static final String PATH_TO_FILE = "path/to/file";

    @Mock
    private NewsFeedValidator validator;

    @Mock
    private ObjectMapper mockMapper;

    @Mock
    private ObjectWriter objectWriter;

    @Mock
    private Converter converter;

    private NewsFeedProcessor underTest;

    @Captor
    private ArgumentCaptor<ProcessedFeedContainer> argumentCaptor;

    @Before
    public void setUp() throws Exception {
        List eventConverters = new ArrayList();
        eventConverters.add(converter);
        underTest = new NewsFeedProcessor(mockMapper, eventConverters);

        when(converter.canConvert(any(EventType.class))).thenReturn(true);
        when(mockMapper.readValue(new File(PATH_TO_FILE), RawFeedContainer.class)).thenReturn(TestHelper.createNewsFeedContainer());
        when(mockMapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
        when(converter.convert(any(RawEvent.class), any(Platform.class))).thenReturn(TestHelper.createProcessedFriendEvent());
    }

    @Test
    public void processFeedShouldDeserializeCorrectFileName() throws Exception {
        final String[] args = {PATH_TO_FILE, Platform.android.name()};
        underTest.processFeed(args);

        verify(mockMapper).readValue(new File(PATH_TO_FILE), RawFeedContainer.class);
    }

    @Test
    public void processFeedShouldWriteFileWithCorrectPath() throws Exception {
        final String[] args = {PATH_TO_FILE, Platform.android.name()};
        underTest.processFeed(args);

        verify(objectWriter).writeValue(eq(new File("path/to/processed-news-feed.json")), anyCollectionOf(ProcessedFriendEvent.class));
    }

    @Test
    public void processFeedShouldWriteFileWithItems() throws Exception {
        final String[] args = {PATH_TO_FILE, Platform.android.name()};
        underTest.processFeed(args);

        verify(objectWriter).writeValue(eq(new File("path/to/processed-news-feed.json")), argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), equalTo(TestHelper.createEventFeedContainer()));

    }

    @Test(expected = NewsFeedException.class)
    public void processFeedShouldThrowNewsFeedExceptionWhenFileCanNotBeWritten() throws Exception {
        final String[] args = {PATH_TO_FILE, Platform.android.name()};
        doThrow(new IOException()).when(objectWriter).writeValue(any(File.class), any(ProcessedFeedContainer.class));

        underTest.processFeed(args);
    }

    @Test(expected = NewsFeedException.class)
    public void processFeedShouldThrowNewsFeedExceptionWhenFileCanNotBeRead() throws Exception {
        final String[] args = {PATH_TO_FILE, Platform.android.name()};
        doThrow(new IOException()).when(mockMapper).readValue(new File(PATH_TO_FILE), RawFeedContainer.class);

        underTest.processFeed(args);
    }
}