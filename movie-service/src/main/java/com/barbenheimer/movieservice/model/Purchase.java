package com.barbenheimer.movieservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="customer_id")
//    private Customer customer;

    private String paymentIntentId;

    @OneToOne
    private CustomerDetail customerDetail;

    private Long paidAmount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "purchase")
    private List<SeatStatus> seatStatuses;

    private LocalDateTime dateTime;

    public Purchase(Long paidAmount){
        this.paidAmount = paidAmount;
    }






}
