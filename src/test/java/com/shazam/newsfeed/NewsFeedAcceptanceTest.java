package com.shazam.newsfeed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class NewsFeedAcceptanceTest {
    private static final String INPUT_FILE = "src/test/resources/sampleFeed.json";
    private static final Platform IOS = Platform.ios;
    private static final String OUTPUT_FILE_NAME = "processed-news-feed.json";
    private static final String OUTPUT_FILE = "src/test/resources/" + OUTPUT_FILE_NAME;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    public void printsUsageForNoArguments() throws Exception {
        NewsFeed.main(new String[]{});
        assertThat(out.toString(), containsString("Please supply a filename for the json file and a platform (ios or android) to process for"));
    }

    @Test
    public void printsFileDoesNotExistFileIfFileDoesNotExist() {
        NewsFeed.main(new String[]{"invalidFile", IOS.name()});
        assertThat(out.toString(), containsString("File is not valid : invalidFile"));
    }

    @Test
    public void printsInvalidPlatformForInvalidPlatform() {
        NewsFeed.main(new String[]{INPUT_FILE, "invalidPlatform"});
        assertThat(out.toString(), containsString("Platform is not valid : invalidPlatform"));
    }

    @Test
    public void shouldWriteFileInSameDirectoryAsUnprocessedNewsFeed() throws IOException {
        NewsFeed.main(new String[]{INPUT_FILE, IOS.name()});
        File outputFile = new File(format("src/test/resources/%s", OUTPUT_FILE_NAME));
        assertTrue(outputFile.isFile());
    }

    @Test
    public void jsonOutputFileShouldMatchExpectedForAndroid() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        NewsFeed.main(new String[]{INPUT_FILE, Platform.android.toString()});

        final Map actual = mapper.readValue(new File(OUTPUT_FILE), Map.class);
        final Map expected = mapper.readValue(new File("src/test/resources/expectedAndroidOutput.json"), Map.class);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void jsonOutputFileShouldMatchExpectedForIos() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        NewsFeed.main(new String[]{INPUT_FILE, Platform.ios.toString()});

        final Map actual = mapper.readValue(new File(OUTPUT_FILE), Map.class);
        final Map expected = mapper.readValue(new File("src/test/resources/expectedIosOutput.json"), Map.class);

        assertThat(actual, equalTo(expected));
    }

    @After
    public void cleanup() {
        System.setOut(null);
        File outputFile = new File(format("src/test/resources/%s", OUTPUT_FILE_NAME));
        outputFile.delete();
    }

}
