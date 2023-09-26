package com.distinction.barbenheimer.service;


import com.distinction.barbenheimer.model.OngoingPurchase;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class OngoingPurchaseServiceImpl implements OngoingPurchaseService {


    @Override
    public String createCustomerIdentifyingToken(){
        StringBuilder token = new StringBuilder();
        long currentTimeInMillisecond = Instant.now().toEpochMilli();
        token.append(currentTimeInMillisecond).append(UUID.randomUUID().toString());
        return token.toString().replace("-","");
    }

}
