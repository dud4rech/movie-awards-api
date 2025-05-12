package com.razzies.movie_awards_api.controller;

import com.razzies.movie_awards_api.entity.AwardInterval;
import com.razzies.movie_awards_api.entity.Movie;
import com.razzies.movie_awards_api.repository.MovieRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Operation(summary = "Get a list of movies", description = "Returns all the movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping("/movies")
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @Operation(summary = "Get a movie by id", description = "Returns a movie as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The movie was not found")
    })
    @GetMapping("/movie/{id}")
    public ResponseEntity<?> listSingleMovie(@PathVariable(value="id") long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(movieRepository.findById(id));
        }
    }

    @Operation(summary = "Get specific data",
            description = "Returns the minimal and maximum consecutive awards interval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @GetMapping("/awards")
    public ResponseEntity<Map<String, List<AwardInterval>>> listAwardsInterval() {
        List<AwardInterval> minInterval = movieRepository.findProducerWithMinConsecutiveAwardsInterval();
        List<AwardInterval> maxInterval = movieRepository.findProducerWithMaxConsecutiveAwardsInterval();

        Map<String, List<AwardInterval>> response = new HashMap<>();
        response.put("min", minInterval);
        response.put("max", maxInterval);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a movie by id", description = "Updates a movie as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The movie was not found")
    })
    @PutMapping("/movie")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie)  {
        try {
            movieRepository.save(movie);
            return ResponseEntity.status(HttpStatus.OK).body(movie);
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error in request body: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete a movie by id", description = "Deletes a movie as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The movie was not found")
    })
    @DeleteMapping("/movie/{id}")
    public String deleteMovie(@PathVariable Long id) {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isEmpty()) {
            return "Movie not found.";
        } else {
            movieRepository.deleteById(id);
            return "Deleted employee with id: " + id;
        }
    }
}
