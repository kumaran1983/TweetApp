package com.salesforce.tweetapi.service;

import com.salesforce.tweetapi.entity.HashTag;
import com.salesforce.tweetapi.resource.entity.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class TweetService implements ITweetService {

    @Autowired
    IHashTagExtractor hashTagExtractor;

    /*
      Approach 1 : Below approach uses Java streams. We are using parallel streams and performing parallel reduction.
     */
    public List<HashTag> computeTopHashTags(List<Tweet> tweets, int hashTagLimit) {

       //Extract all hash tags
       Stream<String> hashTags = tweets.parallelStream()
                   .filter(tweet -> tweet.getText()!= null) //Skip the tweet when text attribute not present
                   .map(tweet -> hashTagExtractor.extractHashTags(tweet))
                   .flatMap(r -> r.stream()); //Perform flatmap to convert Stream<Set<String>> to Stream<String>

       //Calculate count by hash tag
       Map<String, Long> countByHashTag = hashTags.collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));

       //Pick top hash tags
       return getTopHashTags(countByHashTag, hashTagLimit);

    }


    /*
    Approach 2 : Iterate through the tweets sequentially
    */
    public List<HashTag> computeTopHashTags2(List<Tweet> tweets, int limit) {

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
        return getTopHashTags(countByHashTag, limit);

    }

    private List<HashTag> getTopHashTags(Map<String,Long> countByHashTag, int hashTagLimit) {

        //Here the approach is to use a minHeap. Another approach is to sort the map and pick the top 10.

        //Using a min heap provides better time complexity. (N log L) where N is the number of hash tags and L is the limit
        PriorityQueue<HashTag> queue = new PriorityQueue<>(
                (tag1, tag2) -> {
                    if(tag1.getCount() == tag2.getCount()) {
                        return tag2.getHashtag().compareTo(tag1.getHashtag());
                    }else if(tag1.getCount() > tag2.getCount()) {
                        return 1;
                    }else {
                        return -1;
                    }
                }
        );


        for (Map.Entry<String, Long> entry : countByHashTag.entrySet()) {

            //Adding the hash tags to a minimum heap
            queue.offer(new HashTag(entry.getKey(),entry.getValue()));

            if (queue.size() > hashTagLimit) {
                //By popping we keep the queue holding only the top results
                queue.poll();
            }
        }

        List<HashTag> topHashTags = new ArrayList<>();
        while(!queue.isEmpty()){
            topHashTags.add(queue.poll());
        }

        //Since the elements are added from minimum heap the list needs to be reversed
        Collections.reverse(topHashTags);

        return topHashTags;
    }


}
