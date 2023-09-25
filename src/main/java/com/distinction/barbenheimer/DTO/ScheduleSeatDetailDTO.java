package com.distinction.barbenheimer.DTO;

import java.util.List;

import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.SeatStatus;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleSeatDetailDTO {
 
    private String rowCharacter;

    private int columnNumber;

    private int x;

    private int y;

    private int state;

}
