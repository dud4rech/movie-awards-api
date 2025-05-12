package com.razzies.movie_awards_api.repository;

import com.razzies.movie_awards_api.entity.AwardInterval;
import com.razzies.movie_awards_api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findById(Long id);

    @Query(value = "select m1.producers, m1.`year` - m2.`year`, m1.`year`, m2.`year` from movies m1 join movies m2 on m1.producers = m2.producers and m1.`year` > m2.`year` "
            + "where m1.`year`- m2.`year` = "
            + "(select max(m1.`year`- m2.`year`) from movies m1 join movies m2 on m1.producers = m2.producers and m1.`year` > m2.`year` and m1.winner is true) "
            + "group by m1.id "
            + "having max(m1.`year` - m2.`year`) > 0", nativeQuery = true)
    List<AwardInterval> findProducerWithMaxConsecutiveAwardsInterval();

    @Query(value = "select m1.producers, m1.`year` - m2.`year`, m1.year, m2.year from movies m1 join movies m2 on m1.producers = m2.producers and m1.`year` > m2.`year` "
            + "where m1.`year`- m2.`year` = "
            + "(select min(m1.`year`- m2.`year`) from movies m1 join movies m2 on m1.producers = m2.producers and m1.`year` > m2.`year` and m1.winner is true) "
            + "group by m1.id "
            + "having min(m1.`year`- m2.`year`) > 0", nativeQuery = true)
    List<AwardInterval> findProducerWithMinConsecutiveAwardsInterval();
}
