package com.salesforce.tweetapi.extractor;

import com.salesforce.tweetapi.entity.HashTag;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TopHashTagFilter implements ITopHashTagFilter {

    public List<HashTag> getTopHashTags(Map<String,Long> countByHashTag, int hashTagLimit) {

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
