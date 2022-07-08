package com.anlohse.raspberry_awards.services;

import com.anlohse.raspberry_awards.repositories.MovieRepository;
import com.anlohse.raspberry_awards.vo.AwardVO;
import com.anlohse.raspberry_awards.vo.AwardsResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    public final MovieRepository movieRepository;

    public AwardsResultVO findAwards() {
        Map<String, List<AwardVO>> awardsGroupedByProducer = movieRepository.findConsecutiveAwards().stream()
                .collect(Collectors.groupingBy(AwardVO::getProducer));
        List<AwardVO> awards = awardsGroupedByProducer.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1) // precisa ter mais de uma premiação consecutiva
                .map(entry -> mapConsecutiveAwardIntervals(entry.getKey(), entry.getValue()))
                .flatMap(List::stream)
                .sorted(Comparator.comparing(AwardVO::getInterval)) // ordena em ordem crescente de intervalo
                .collect(Collectors.toList());
        List<AwardVO> max = new ArrayList<>();
        List<AwardVO> min = new ArrayList<>();
        if (!awards.isEmpty()) {
            AwardVO minor = awards.get(0);
            min = awards.stream()
                    .filter(award -> award.getInterval() == minor.getInterval())
                    .collect(Collectors.toList());
            AwardVO greater = awards.get(awards.size()-1);
            max = awards.stream()
                    .filter(award -> award.getInterval() == greater.getInterval())
                    .collect(Collectors.toList());
        }
        return AwardsResultVO.builder().min(min).max(max).build();
    }

    private List<AwardVO> mapConsecutiveAwardIntervals(String producer, List<AwardVO> list) {
        list.sort(Comparator.comparing(AwardVO::getPreviousWin));
        List<AwardVO> tempList = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            AwardVO award1 = list.get(i);
            AwardVO award2 = list.get(i+1);
            tempList.add(AwardVO.builder()
                    .producer(producer)
                    .previousWin(award1.getPreviousWin())
                    .followingWin(award2.getFollowingWin())
                    .interval(award2.getFollowingWin() - award1.getPreviousWin())
                    .build());
        }
        return tempList;
    }
}
