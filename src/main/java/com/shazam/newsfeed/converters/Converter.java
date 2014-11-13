package com.shazam.newsfeed.converters;

import com.shazam.newsfeed.*;
import com.shazam.newsfeed.model.ProcessedEvent;
import com.shazam.newsfeed.model.RawEvent;
import com.shazam.newsfeed.validation.NewsFeedException;

public interface Converter {

    public boolean canConvert(EventType eventType);

    public ProcessedEvent convert(RawEvent rawEvent, Platform platform) throws NewsFeedException;
}
