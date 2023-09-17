package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.MovieScheduleDetailDTO;
import com.distinction.barbenheimer.DTO.MovieScheduleDateDetailDTO;
import com.distinction.barbenheimer.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class MovieScheduleServiceImpl implements MovieScheduleService {
    private ModelMapper modelMapper;

    @Autowired
    public MovieScheduleServiceImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


    @Override
    public List<MovieScheduleDetailDTO> getAll(Movie movie){
        return null;
    }

    @Override

    public List<MovieScheduleDateDetailDTO> getShowtime(Movie movie){
        return null;
    }

    @Override

    public List<MovieScheduleDateDetailDTO> getShowtime(Movie movie, LocalDateTime before){
        return null;
    }




}
