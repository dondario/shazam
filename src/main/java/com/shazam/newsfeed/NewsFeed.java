package com.shazam.newsfeed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shazam.newsfeed.converters.FriendConverter;
import com.shazam.newsfeed.converters.NewsConverter;
import com.shazam.newsfeed.validation.NewsFeedException;
import com.shazam.newsfeed.validation.NewsFeedParamException;
import com.shazam.newsfeed.validation.NewsFeedValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point to the News Feed app.
 */
public class NewsFeed {

	public static void main(String[] args) {
        NewsFeedValidator validator = new NewsFeedValidator();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);



        List eventConverters = new ArrayList();
        eventConverters.add(new FriendConverter());
        eventConverters.add(new NewsConverter());

        NewsFeedProcessor newsFeedProcessor = new NewsFeedProcessor(mapper, eventConverters);

        try {
            validator.validate(args);
            newsFeedProcessor.processFeed(args);
        } catch (NewsFeedParamException e) {
            System.out.println(e.getMessage());
            return;
        } catch (NewsFeedException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}