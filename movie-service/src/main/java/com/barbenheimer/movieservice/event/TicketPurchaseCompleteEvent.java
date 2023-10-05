package com.barbenheimer.movieservice.event;

import com.barbenheimer.movieservice.model.Purchase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class TicketPurchaseCompleteEvent extends ApplicationEvent {

    private Purchase purchase;

    public TicketPurchaseCompleteEvent(Purchase purchase){
        super(purchase);
        this.purchase = purchase;
    }

}
