package com.anlohse.raspberry_awards.controllers;

import com.anlohse.raspberry_awards.domain.Movie;
import com.anlohse.raspberry_awards.services.MovieService;
import com.anlohse.raspberry_awards.vo.AwardsResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<AwardsResultVO> findAwards() {
        return ResponseEntity.ok(movieService.findAwards());
    }

}
