package com.salesforce.tweetapi.resource.entity;

import com.salesforce.tweetapi.entity.HashTag;

import java.util.List;

public class HashTagResponse extends BaseResponse {

    private List<HashTag> topTags;

    public List<HashTag> getTopTags() {
        return topTags;
    }

    public void setTopTags(List<HashTag> topTags) {
        this.topTags = topTags;
    }



}
