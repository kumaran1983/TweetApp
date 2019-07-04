package com.salesforce.tweetapi.extractor;

import com.salesforce.tweetapi.resource.entity.Tweet;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HashTagExtractor implements IHashTagExtractor {


    public Set<String> extractHashTags(Tweet tweet) {

        //Using set to filter out duplicate hash tags in tweet.
        Set<String> hashTags = new HashSet<String>();

        if(tweet.getText() == null) {
            return hashTags;
        }
        //Have used the Pattern provided in the skeleton code to filter out hash tags.
        //Below pattern works good only for english characters. Non english and numbers will be excluded.
        //Assumption : hash tags contain only english characters.
        Matcher hashTagMatcher = Pattern.compile("#([a-z]+)", Pattern.CASE_INSENSITIVE).matcher(tweet.getText());

        while(hashTagMatcher.find()) {
            hashTags.add(hashTagMatcher.group(1));
        }

        return hashTags;
    }


}
