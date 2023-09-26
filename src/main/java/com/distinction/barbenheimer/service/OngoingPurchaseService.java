package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.OngoingPurchaseDetailDTO;
import com.distinction.barbenheimer.DTO.OngoingPurchaseTokenDTO;

public interface OngoingPurchaseService {

    public String createCustomerIdentifyingToken();

    public OngoingPurchaseDetailDTO getDetail(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO);

}
