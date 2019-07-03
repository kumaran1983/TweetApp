package com.salesforce.tweetapi.resource;

import com.salesforce.tweetapi.entity.HashTag;
import com.salesforce.tweetapi.resource.entity.*;
import com.salesforce.tweetapi.service.ITweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TweetController {

    @Autowired
    ITweetService tweetService;

    //The max number of results which can requested from application.properties
    @Value( "${limit.max}" )
    private int maxLimit;

    @RequestMapping(value = "/tweets", method = RequestMethod.POST)
    public ResponseEntity<HashTagResponse> topHashTags(@RequestBody List<Tweet> tweets, @RequestParam(value = "limit", defaultValue = "10") final Integer limit) {

        HashTagResponse hashTagResponse = new HashTagResponse();

        //Perform validation on limit (Max number of results requested by user)
        //Have validation classes if there are lot of input validations needed.
        if(limit > maxLimit || limit < 1) {
            hashTagResponse.setMessage(new Message(MessageType.ERROR, 1001, "INVALID LIMIT", MessageCategory.APP));
            return new ResponseEntity<>(hashTagResponse,HttpStatus.BAD_REQUEST);
        }

        try {
            List<HashTag> hashTags = tweetService.computeTopHashTags(tweets, limit);
            hashTagResponse.setTopTags(hashTags);
            return new ResponseEntity<>(hashTagResponse, HttpStatus.OK);
        } catch (Exception ex) {
            hashTagResponse.setMessage(new Message(MessageType.ERROR, 1000, "SYSTEM ERROR", MessageCategory.SYSTEM));
            return new ResponseEntity<>(hashTagResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
