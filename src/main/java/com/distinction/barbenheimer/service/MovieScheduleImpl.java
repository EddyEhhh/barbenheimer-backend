package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.DTO.MovieScheduleDetailDTO;
import com.distinction.barbenheimer.DTO.MovieScheduleShowtimeDTO;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.MovieSchedule;
import com.distinction.barbenheimer.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MovieScheduleImpl implements MovieScheduleService {
    private ModelMapper modelMapper;

    @Autowired
    public MovieScheduleImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


    @Override
    public List<MovieScheduleDetailDTO> getAll(Movie movie){
        return null;
    }

    @Override

    public List<MovieScheduleShowtimeDTO> getShowtime(Movie movie){
        return null;
    }

    @Override

    public List<MovieScheduleShowtimeDTO> getShowtime(Movie movie, LocalDateTime before){
        return null;
    }




}
