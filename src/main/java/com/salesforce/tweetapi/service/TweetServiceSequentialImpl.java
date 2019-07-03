package com.salesforce.tweetapi.service;

import com.salesforce.tweetapi.entity.HashTag;
import com.salesforce.tweetapi.extractor.ITopHashTagFilter;
import com.salesforce.tweetapi.resource.entity.Tweet;
import com.salesforce.tweetapi.extractor.IHashTagExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Qualifier("tweetServiceSequential")
public class TweetServiceSequentialImpl implements  ITweetService {

    @Autowired
    IHashTagExtractor hashTagExtractor;

    @Autowired
    ITopHashTagFilter topHashTagFilter;

    public List<HashTag> computeTopHashTags(List<Tweet> tweets, int limit) {

        Map<String,Long> countByHashTag = new HashMap<String,Long>();

        for(Tweet tweet : tweets) {

            //Skip the tweet if the text is not present
            if(tweet.getText() == null) {
                continue;
            }

            Set<String> tagsInTweet = hashTagExtractor.extractHashTags(tweet);

            for(String hashTag : tagsInTweet) {
                if(countByHashTag.get(hashTag) == null) {
                    countByHashTag.put(hashTag,1l);
                }else {
                    countByHashTag.put(hashTag,countByHashTag.get(hashTag)+1);
                }
            }
        }

        //Pick top hash tags
        return topHashTagFilter.getTopHashTags(countByHashTag, limit);

    }

}
