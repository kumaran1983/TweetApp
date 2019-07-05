package com.salesforce.tweetapi.service;


import com.salesforce.tweetapi.entity.HashTag;
import com.salesforce.tweetapi.extractor.HashTagExtractor;
import com.salesforce.tweetapi.extractor.IHashTagExtractor;
import com.salesforce.tweetapi.extractor.TopHashTagFilter;
import com.salesforce.tweetapi.resource.entity.Tweet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {

    @InjectMocks
    private TweetServiceParallelImpl serviceParallel;

    @InjectMocks
    private TweetServiceSequentialImpl serviceSequential;

    @Spy
    private HashTagExtractor hashTagExtractor;

    @Spy
    private TopHashTagFilter topHashTagFilter;

    @Test
    public void testParallelProcessing() throws Exception {
        List<HashTag> hashTags =  serviceParallel.computeTopHashTags(populateTweets(), 10);
        assertResults(hashTags);
    }

    @Test
    public void testSequentialProcessing() {
        List<HashTag>  hashTags = serviceSequential.computeTopHashTags(populateTweets(), 10);
        assertResults(hashTags);
    }

    private void assertResults(List<HashTag> hashTags) {
        Assert.assertTrue(hashTags.size() == 4);
        Assert.assertTrue(hashTags.get(0).getHashtag().equals("neural"));
        Assert.assertTrue(hashTags.get(0).getCount() == 2);

        Assert.assertTrue(hashTags.get(1).getHashtag().equals("input"));
        Assert.assertTrue(hashTags.get(1).getCount() == 1);

        Assert.assertTrue(hashTags.get(2).getHashtag().equals("online"));
        Assert.assertTrue(hashTags.get(2).getCount() == 1);

        Assert.assertTrue(hashTags.get(3).getHashtag().equals("override"));
        Assert.assertTrue(hashTags.get(3).getCount() == 1);
    }



    private List<Tweet> populateTweets() {
        Tweet t1 = new Tweet();
        t1.setText("The XML array is down, index the online interface so we can bypass the COM protocol! #neural #input #online");
        Tweet t2 = new Tweet();
        t2.setText("You can't parse the alarm without indexing the neural XSS alarm! #override #neural");

        List<Tweet> tweets = new ArrayList<>();
        tweets.add(t1);
        tweets.add(t2);

        return tweets;
    }

}
