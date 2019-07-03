package com.salesforce.tweetapi.extractor;

import com.salesforce.tweetapi.resource.entity.Tweet;

import java.util.Set;

public interface IHashTagExtractor {

    public Set<String> extractHashTags(Tweet tweet);

}
