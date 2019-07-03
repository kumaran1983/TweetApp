package com.salesforce.tweetapi.service;

import com.salesforce.tweetapi.entity.HashTag;
import com.salesforce.tweetapi.resource.entity.Tweet;
import com.salesforce.tweetapi.extractor.IHashTagExtractor;
import com.salesforce.tweetapi.extractor.ITopHashTagFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@Qualifier("tweetServiceParallel")
public class TweetServiceParallelImpl implements ITweetService {

    @Autowired
    IHashTagExtractor hashTagExtractor;

    @Autowired
    ITopHashTagFilter topHashTagFilter;

    /*
      Approach 1 : Below approach uses Java streams. Using parallel stream.
      TODO: Run performance test
     */
    public List<HashTag> computeTopHashTags(List<Tweet> tweets, int hashTagLimit) {

       //Extract all hash tags
       Stream<String> hashTags = tweets.parallelStream()
                   .filter(tweet -> tweet.getText()!= null) //Skip the tweet when text attribute not present
                   .map(tweet -> hashTagExtractor.extractHashTags(tweet))
                   .flatMap(r -> r.stream()); //Perform flatmap to convert Stream<Set<String>> to Stream<String>

       //Calculate count by hash tag
       Map<String, Long> countByHashTag = hashTags.collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));

       //Pick top hash tags
       return topHashTagFilter.getTopHashTags(countByHashTag, hashTagLimit);

    }




}
