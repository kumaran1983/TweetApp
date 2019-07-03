package com.salesforce.tweetapi.service;

import com.salesforce.tweetapi.resource.entity.Tweet;
import com.salesforce.tweetapi.extractor.HashTagExtractor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class HashTagExtractorTest {


    @Test
    public void testWithTweetContainingHashtag() {

        Tweet tweet = new Tweet();
        tweet.setText("you can't connect the matrix without navigating the multi-byte agp firewall! #opensource #crossplatform #opensource");

        HashTagExtractor extractor = new HashTagExtractor();
        Set<String> hashTags = extractor.extractHashTags(tweet);
        Assert.assertEquals(2, hashTags.size());
        Assert.assertTrue(hashTags.contains("opensource"));
        Assert.assertTrue(hashTags.contains("crossplatform"));
    }


    @Test
    public void testWithTweetContainingNoHashtag() {

        Tweet tweet = new Tweet();
        tweet.setText("you can't connect the matrix without navigating the multi-byte agp firewall! #12");

        HashTagExtractor extractor = new HashTagExtractor();
        Set<String> hashTags = extractor.extractHashTags(tweet);
        Assert.assertEquals(0, hashTags.size());
    }

    @Test
    public void testWithTweetContainingNoText() {

        Tweet tweet = new Tweet();

        HashTagExtractor extractor = new HashTagExtractor();
        Set<String> hashTags = extractor.extractHashTags(tweet);
        Assert.assertEquals(0, hashTags.size());
    }

}
