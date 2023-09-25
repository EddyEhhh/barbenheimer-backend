package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @OneToOne
    private CustomerDetail customerDetail;

    @OneToMany(fetch = FetchType.EAGER)
    private List<SeatStatus> seatStatuses;

    private double paidAmount;

    private LocalDateTime dateTime;






}
