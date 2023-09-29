package com.distinction.barbenheimer.event;

import com.distinction.barbenheimer.listener.TicketPurchaseCompleteEventListener;
import com.distinction.barbenheimer.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
