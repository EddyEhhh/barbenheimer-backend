package com.distinction.barbenheimer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieTitleDTO {
    public Long movieId;

    public String movieTitle;
}
