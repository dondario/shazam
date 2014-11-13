package com.shazam.newsfeed;

import com.shazam.newsfeed.validation.NewsFeedParamException;
import com.shazam.newsfeed.validation.NewsFeedValidator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NewsFeedValidatorTest {
    private static final String FILE_NAME = "src/test/resources/sampleFeed.json";
    private static final Platform PLATFORM = Platform.ios;
    private NewsFeedValidator underTest;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        underTest = new NewsFeedValidator();
    }

    @Test
    public void validateShouldThrowNewsFeedParamExceptionWhenInvalidNumberOfParams() throws Exception {
        thrown.expect(NewsFeedParamException.class);
        thrown.expectMessage("Please supply a filename for the json file and a platform (ios or android) to process for");
        underTest.validate(new String[]{});
    }

    @Test
    public void validateShouldThrowNewsFeedParamExceptionWhenInvalidFileIsInvalid() throws NewsFeedParamException {
        thrown.expect(NewsFeedParamException.class);
        thrown.expectMessage("File is not valid : invalidFile");

        underTest.validate(new String[]{"invalidFile", PLATFORM.name()});
    }

    @Test
    public void validateShouldThrowNewsFeedParamExceptionWhenPlatformIsInvalid() throws NewsFeedParamException {
        thrown.expect(NewsFeedParamException.class);
        thrown.expectMessage("Platform is not valid : invalidPlatform");
        underTest.validate(new String[]{FILE_NAME, "invalidPlatform"});

    }

    @Test
    public void validateShouldAcceptAndroidPlatform() throws NewsFeedParamException {
        underTest.validate(new String[]{FILE_NAME, "android"});
    }

    @Test
    public void validateShouldAcceptIosPlatform() throws NewsFeedParamException {
        underTest.validate(new String[]{FILE_NAME, "ios"});
    }
}
