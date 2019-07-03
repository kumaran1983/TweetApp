package com.salesforce.tweetapi.resource;

import com.salesforce.tweetapi.entity.HashTag;
import com.salesforce.tweetapi.resource.entity.*;
import com.salesforce.tweetapi.service.ITweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TweetController {

    public static Logger log = LoggerFactory.getLogger(TweetController.class);

    @Autowired
    @Qualifier("tweetServiceParallel")
    ITweetService tweetService;

    @Value("${limit.default}")
    private int defaultLimit;

    @Value("${limit.max}")
    private int maxLimit;

    @RequestMapping(value = "/tweets", method = RequestMethod.POST)
    public ResponseEntity<HashTagResponse> execute(@RequestBody List<Tweet> tweets, @RequestParam(value = "limit", required=false) Integer limit) {

        HashTagResponse hashTagResponse = new HashTagResponse();

        //Validation of limit request parameter. Limit determines the number of results to return.
        //Can we moved under validation class
        if(limit != null && (limit > maxLimit || limit < 1)) {
            hashTagResponse.setMessage(new Message(MessageType.ERROR, 1001, "INVALID LIMIT"));
            return new ResponseEntity<>(hashTagResponse,HttpStatus.BAD_REQUEST);
        }

        //Limit is not mandatory parameter
        if(limit == null) limit = defaultLimit;

        try {
            List<HashTag> hashTags = tweetService.computeTopHashTags(tweets, limit);
            hashTagResponse.setTopTags(hashTags);
            return new ResponseEntity<>(hashTagResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("TweetController Error : ", ex);
            hashTagResponse.setMessage(new Message(MessageType.ERROR, 1000, "SYSTEM ERROR"));
            return new ResponseEntity<>(hashTagResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
