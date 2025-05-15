package com.razzies.movie_awards_api.repository;

import com.razzies.movie_awards_api.entity.AwardInterval;
import com.razzies.movie_awards_api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findById(Long id);

    @Query(value = "select m.producers, abs(w.year - m.year), m.year, w.year from winners w "
            + "inner join movies m on m.producers = w.name and m.year <> w.year and m.winner is true "
            + "where abs(w.year - m.year) = "
            + "(select max(abs(w1.year - m1.year)) from winners w1 inner join movies m1 on w1.name = m1.producers and m1.year <> w1.year and m1.winner is true) "
            + "group by m.id "
            + "having abs(w.year - m.year) > 0", nativeQuery = true)
    List<AwardInterval> findProducerWithMaxConsecutiveAwardsInterval();

    @Query(value = "select m.producers, abs(w.year - m.year), w.year, m.year from winners w "
            + "inner join movies m on m.producers = w.name and m.year <> w.year and m.winner is true "
            + "where abs(w.year - m.year) = "
            + "(select min(abs(w1.year - m1.year)) from winners w1 inner join movies m1 on w1.name = m1.producers and m1.year <> w1.year and m1.winner is true) "
            + "group by m.id "
            + "having abs(w.year - m.year) > 0", nativeQuery = true)
    List<AwardInterval> findProducerWithMinConsecutiveAwardsInterval();
}
