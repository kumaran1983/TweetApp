package com.salesforce.tweetapi.resource.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {

    @JsonProperty("id")
    private long id;

    @JsonProperty("id_str")
    private String idStr;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("favorited")
    private boolean favorited;

    @JsonProperty("favorite_count")
    private int favoriteCount;

    @JsonProperty("retweet_count")
    private int retweetCount;

    @JsonProperty("retweeted")
    private boolean retweeted;

    @JsonProperty("truncated")
    private boolean truncated;

    @JsonProperty("text")
    private String text;

    @JsonProperty("source")
    private String source;

    public long getId() {
        return id;
    }

    public String getIdStr() {
        return idStr;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }
}