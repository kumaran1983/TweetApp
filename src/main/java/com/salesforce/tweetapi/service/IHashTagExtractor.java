package com.salesforce.tweetapi.service;

import com.salesforce.tweetapi.resource.entity.Tweet;

import java.util.Set;

public interface IHashTagExtractor {

    public Set<String> extractHashTags(Tweet tweet);

}
