package com.salesforce.tweetapi.extractor;

import com.salesforce.tweetapi.entity.HashTag;

import java.util.List;
import java.util.Map;

public interface ITopHashTagFilter {

    public List<HashTag>  getTopHashTags(Map<String,Long> countByHashTag, int resultLimit);
}
