package com.salesforce.tweetapi.entity;

public class HashTag  {

    String hashtag;
    long count;

    public HashTag() {}

    public HashTag(String hashtag, long count) {
        super();
        this.hashtag = hashtag;
        this.count = count;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
