package com.shazam.newsfeed.validation;

import com.shazam.newsfeed.Platform;

import java.io.File;

import static java.lang.String.format;

public class NewsFeedValidator {

    private static final int EXPECTED_NUMBER_OF_ARGS = 2;

    public NewsFeedValidator() {
    }

    public void validate(String[] args) throws NewsFeedParamException {
        validateNumberOfArgs(args);
        validateFile(args[0]);
        validatePlatform(args[1]);
    }

    private void validatePlatform(final String platformString) throws NewsFeedParamException {
        try {
            Platform.valueOf(platformString);
        } catch (IllegalArgumentException e) {
            throw new NewsFeedParamException(format("Platform is not valid : %s", platformString));
        }
    }

    private void validateFile(final String fileName) throws NewsFeedParamException {
        if (!new File(fileName).isFile()) {
            throw new NewsFeedParamException(format("File is not valid : %s", fileName));
        }
    }

    private void validateNumberOfArgs(final String[] args) throws NewsFeedParamException {
        if (args.length != EXPECTED_NUMBER_OF_ARGS) {
            throw new NewsFeedParamException("Please supply a filename for the json file and a platform (ios or android) to process for");
        }
    }
}
