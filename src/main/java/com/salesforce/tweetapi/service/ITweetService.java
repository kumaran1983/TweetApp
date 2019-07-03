package com.salesforce.tweetapi.service;

import com.salesforce.tweetapi.entity.HashTag;
import com.salesforce.tweetapi.resource.entity.Tweet;

import java.util.List;

public interface ITweetService {

    public List<HashTag> computeTopHashTags(List<Tweet> tweets, int limit);
}
