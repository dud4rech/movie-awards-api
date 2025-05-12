package com.razzies.movie_awards_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.razzies.movie_awards_api.entity.AwardInterval;
import com.razzies.movie_awards_api.entity.Movie;
import com.razzies.movie_awards_api.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieRepository movieRepository;
    Movie movie;
    AwardInterval awardInterval;
    List<Movie> movieList = new ArrayList<>();
    List<AwardInterval> awardIntervalList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movieList.add(movie);
        awardInterval = new AwardInterval("Producer 1", 1, 2010, 2011);
        awardIntervalList.add(awardInterval);
    }

    @Test
    void listMovies() throws Exception {
        when(movieRepository.findAll()).thenReturn(movieList);
        this.mockMvc.perform(get("/api/movies"))
            .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void listSingleMovie_whenMovieIsFound() throws Exception {
        when(movieRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(movie));
        this.mockMvc.perform(get("/api/movie/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void listSingleMovie_whenMovieIsNotFound() throws Exception {
        when(movieRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/movie/1"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void listAwardsInterval() throws Exception {
        when(movieRepository.findProducerWithMinConsecutiveAwardsInterval()).thenReturn(awardIntervalList);
        when(movieRepository.findProducerWithMaxConsecutiveAwardsInterval()).thenReturn(awardIntervalList);

        LinkedHashMap<String, List<AwardInterval>> expectedResponse = new LinkedHashMap<>();
        expectedResponse.put("min", awardIntervalList);
        expectedResponse.put("max", awardIntervalList);

        this.mockMvc.perform(get("/api/awards"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.min", isA(List.class)))
                .andExpect(jsonPath("$.max", isA(List.class)))
                .andExpect(jsonPath("$.min[0].producers", is(awardInterval.getProducers())))
                .andExpect(jsonPath("$.min[0].interval", is(awardInterval.getInterval())))
                .andExpect(jsonPath("$.min[0].previousWin", is(awardInterval.getPreviousWin())))
                .andExpect(jsonPath("$.min[0].followingWin", is(awardInterval.getFollowingWin())));
    }

    @Test
    void updateMovie_whenSucceds() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(movie);

        when(movieRepository.save(movie))
                .thenReturn(movie);
        this.mockMvc.perform(put("/api/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteMovie_whenMovieIsFound() throws Exception {
        when(movieRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(movie));
        this.mockMvc.perform(delete("/api/movie/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteMovie_whenMovieNotFound() throws Exception {
        when(movieRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());
        this.mockMvc.perform(delete("/api/movie/1"))
                .andDo(print()).andExpect(status().isOk());
    }
}