package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.dto.OngoingPurchaseDetailDTO;
import com.distinction.barbenheimer.dto.OngoingPurchaseTokenDTO;

public interface OngoingPurchaseService {

    public String createCustomerIdentifyingToken();

    public OngoingPurchaseDetailDTO getDetail(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO);

}
