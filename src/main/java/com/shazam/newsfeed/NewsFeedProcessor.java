package com.shazam.newsfeed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shazam.newsfeed.converters.Converter;
import com.shazam.newsfeed.model.ProcessedEvent;
import com.shazam.newsfeed.model.ProcessedFeedContainer;
import com.shazam.newsfeed.model.RawEvent;
import com.shazam.newsfeed.model.RawFeedContainer;
import com.shazam.newsfeed.validation.NewsFeedException;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class NewsFeedProcessor {
    public static final String OUTPUT_FILE = "%sprocessed-news-feed.json";
    private ObjectMapper mapper;
    private List<Converter> eventConverters;

    public NewsFeedProcessor(final ObjectMapper mapper, List<Converter> eventConverters) {
        this.mapper = mapper;
        this.eventConverters = eventConverters;
    }

    public void processFeed(final String[] args) throws NewsFeedException {
        String fileName = args[0];
        Platform platform = Platform.valueOf(args[1]);

        final ProcessedFeedContainer processedFeedContainer = new ProcessedFeedContainer();
        processEvents(processedFeedContainer, deserializeFeed(fileName), platform);
        writeFeed(fileName, processedFeedContainer);
    }

    private void writeFeed(String arg, ProcessedFeedContainer processedFeedContainer) throws NewsFeedException {
        final String fileNameAndPath = getFileNameAndPath(arg);

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileNameAndPath), processedFeedContainer);
        } catch (IOException e) {
            throw new NewsFeedException(format("could not write to file: %s", fileNameAndPath));
        }

    }

    private void processEvents(ProcessedFeedContainer processedFeedContainer, RawFeedContainer newsFeed, Platform platform) throws NewsFeedException {
        for (RawEvent rawEvent : newsFeed.getNewsFeed()) {
            ProcessedEvent processedEvent = null;
            for (Converter eventConverter : eventConverters) {
                if (eventConverter.canConvert(rawEvent.getEventType())) {
                    processedEvent = eventConverter.convert(rawEvent, platform);
                }
            }

            processedFeedContainer.addItem(processedEvent);
        }
    }

    private RawFeedContainer deserializeFeed(String fileName) throws NewsFeedException {
        try {
            return mapper.readValue(new File(fileName), RawFeedContainer.class);
        } catch (IOException e) {
            throw new NewsFeedException(format("could not read file : %s", fileName));
        }
    }

    private String getFileNameAndPath(final String arg) {
        return format(OUTPUT_FILE, FilenameUtils.getPath(arg));
    }


}
