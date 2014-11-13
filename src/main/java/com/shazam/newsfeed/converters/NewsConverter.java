package com.shazam.newsfeed.converters;

import com.shazam.newsfeed.EventType;
import com.shazam.newsfeed.Platform;
import com.shazam.newsfeed.model.NewsEvent;
import com.shazam.newsfeed.model.ProcessedEvent;
import com.shazam.newsfeed.model.ProcessedNewsEvent;
import com.shazam.newsfeed.model.RawEvent;
import com.shazam.newsfeed.validation.NewsFeedException;

import static java.lang.String.format;

public class NewsConverter implements Converter {

    @Override
    public boolean canConvert(final EventType eventType) {
        return eventType == EventType.news;
    }

    @Override
    public ProcessedEvent convert(final RawEvent rawEvent, final Platform platform) throws NewsFeedException {
        if (!canConvert(rawEvent.getEventType())) {
            throw new NewsFeedException(format("%s can not be converted by the NewsConverter", rawEvent.getEventType().name()));
        }

        NewsEvent newsEvent = (NewsEvent) rawEvent;

        return new ProcessedNewsEvent(newsEvent.getTimestamp(),
                newsEvent.getTitle(),
                newsEvent.getBody(),
                newsEvent.getUrl(),
                newsEvent.getPurchaseUrl(platform));
    }
}
