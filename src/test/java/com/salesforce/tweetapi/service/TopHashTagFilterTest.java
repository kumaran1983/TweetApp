package com.salesforce.tweetapi.service;

import com.salesforce.tweetapi.entity.HashTag;
import com.salesforce.tweetapi.extractor.TopHashTagFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopHashTagFilterTest {

    @Test
    public void testExtractingTopHashTags() {

        Map<String,Long> countByHashtag = new HashMap<>();
        countByHashtag.put("a",10l);
        countByHashtag.put("b",20l);
        countByHashtag.put("c",30l);

        TopHashTagFilter tweetService = new TopHashTagFilter();
        int limit = 2;
        List<HashTag> hashtags = tweetService.getTopHashTags(countByHashtag,limit);

        Assert.assertEquals(2, hashtags.size());

        List<String> hashTagNames = hashtags.stream().map(r -> r.getHashtag()).collect(Collectors.toList());

        Assert.assertTrue(hashTagNames.contains("b"));
        Assert.assertTrue(hashTagNames.contains("c"));
        Assert.assertFalse(hashTagNames.contains("a"));


    }


}
