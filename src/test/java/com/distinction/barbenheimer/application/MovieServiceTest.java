package com.distinction.barbenheimer.application;

import com.distinction.barbenheimer.repository.MovieRepository;
import com.distinction.barbenheimer.service.MovieServiceImpl;
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
