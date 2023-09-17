package com.distinction.barbenheimer.DTO;

import com.distinction.barbenheimer.model.Seat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HallDTO {

    private Long id;

    private int number;

    private int seatCapacity;

    private List<Seat> seats;

}
