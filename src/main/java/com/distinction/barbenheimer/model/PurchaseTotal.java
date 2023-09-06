package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseTotal {

    @Id
    @OneToOne
    @JoinColumn(name="purchase_id")
    private Purchase purchase;

    @Id
    private float totalPrice;
}
