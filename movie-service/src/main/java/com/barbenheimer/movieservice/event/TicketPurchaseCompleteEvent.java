package com.barbenheimer.movieservice.event;

import com.barbenheimer.movieservice.dto.TicketMailDetailDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class TicketPurchaseCompleteEvent extends ApplicationEvent {

    private TicketMailDetailDTO ticketMailDetailDTO;
//    private String message;

    public TicketPurchaseCompleteEvent(TicketMailDetailDTO ticketMailDetailDTO){
        super(ticketMailDetailDTO);
        this.ticketMailDetailDTO = ticketMailDetailDTO;
//        this.purchase = purchase;
    }

}

