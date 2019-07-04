package com.salesforce.tweetapi.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LimitValidator {

    @Value("${limit.max}")
    private int maxLimit;

    public boolean  validateLimit(Integer limit) {
        if(limit != null && (limit > maxLimit || limit < 1)) {
            return false;
        }
        return true;
    }
}
