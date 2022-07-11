package com.anlohse.raspberry_awards.repositories;

import com.anlohse.raspberry_awards.domain.Movie;
import com.anlohse.raspberry_awards.vo.AwardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "select new com.anlohse.raspberry_awards.vo.AwardVO(a.producer, 0, a.year, b.year) \n" +
            " from Movie a, Movie b\n" +
            " where a.producer = b.producer\n" +
            "   and a.year = b.year - 1\n" +
//            "   and a.winner = 'yes'\n" + // comentado para considerar todos ao invés de somente os vencedores
//            "   and b.winner = 'yes'\n" +
            " order by a.producer, a.year")
    List<AwardVO> findConsecutiveAwards();

}
