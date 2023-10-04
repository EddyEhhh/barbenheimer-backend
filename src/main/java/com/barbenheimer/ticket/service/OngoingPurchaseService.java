package com.barbenheimer.ticket.service;

import com.barbenheimer.ticket.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.ticket.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.ticket.model.OngoingPurchase;

public interface OngoingPurchaseService {

    public String createCustomerIdentifyingToken();

    public OngoingPurchaseDetailDTO getDetail(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO);

    public boolean validateOngoingPurchaseToken(String token);

    public void invalidateAllExpiredPurchaseToken();

}
