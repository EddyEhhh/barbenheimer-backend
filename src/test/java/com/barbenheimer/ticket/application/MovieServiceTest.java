package com.barbenheimer.ticket.application;

import com.barbenheimer.ticket.repository.MovieRepository;
import com.barbenheimer.ticket.serviceImpl.MovieServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movies;

    @InjectMocks
    private MovieServiceImpl movieService;

}

