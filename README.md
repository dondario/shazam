--------------------------------------------------------
   News Feed - Shazam server team technical test
--------------------------------------------------------

The News Team team want to quickly prototype some news feed presentation behaviour.
A command line application will be written to process a file containing json
representing a news feed. The application will output a formatted feed for
a specified platform.

The News Feed app takes two arguments:
* the name of the file containing the news feed to process
* a platform (either 'ios' or 'android')

It should run from the command line in the following way:

`user$ java -jar news-feed.jar news-feed.json ios`

The resulting news feed should be written to a file 'processed-news-feed.json', which 
should be located in the same directory as the unprocessed news feed. The
processed news feed json will contain an array of processed news feed items. You
may assume the input is sorted by timestamp.

The news feed contains items with event type "friend" and "news".

A processed "friend" item should look like:

    {
    	"timestamp": 1410963029064,
    	"title": "Chris Shazamed 'All About That Bass' by Meghan Trainor",
    	"shazamurl": "http://www.shazam.com/discover/track/129681504",
    	"purchaseurl": "http://www.amazon.com/All-About-That-Bass/dp/B00N9HX0R6"
    }

The application should construct a "shazamurl" field with the supplied track ID for 'friend'
items. The purchase url should be picked on the following criteria:
* iOS clients should receive iTunes links
* Android clients should receive Amazon links

A processed "news" item should look like:

    {
    	"timestamp": 1410962009034,
    	"title": "Shazam Single Of The Week",
    	"body": "Robin Schulz is our favourite track this week with 'Prayer in C'",
    	"storyurl": "http://www.shazam.com/discover/track/107273589",
    	"purchaseurl": "https://itunes.apple.com/gb/album/prayer-in-c-robin-schulz-remix/id895147739"
    }

Your program should be able to handle the sample news feed provided (src/test/resources/sampleFeed.json)
and other similar news feeds of the same format.

The News Feed project has a main method entry point 
(com.shazam.newsfeed.NewsFeed), and a simple acceptance test 
(com.shazam.newsfeed.NewsFeedAcceptanceTest) to get you started.

We will be assessing your solution on the following criteria:

* Solution works to the specification
* Solution should build and execute tests from the terminal and from an IDE
* Evidence of a TDD approach
* Clean, maintainable code adhering to SOLID principles

Please package your solution as a zip file. In addition, please write a short explanation of your 
approach including any assumptions you have made.

Good luck!
