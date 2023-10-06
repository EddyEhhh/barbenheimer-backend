package com.barbenheimer.mailerservice.event;

import com.barbenheimer.mailerservice.dto.TicketMailDetailDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class TicketPurchaseCompleteEvent {

    private TicketMailDetailDTO ticketMailDetailDTO;


}

