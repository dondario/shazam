package com.shazam.newsfeed.model;


import java.sql.Timestamp;

public interface ProcessedEvent {
    Timestamp getTimestamp();

    String getTitle();

    String getPurchaseUrl();
}
