package com.anlohse.raspberry_awards.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AwardsResultVO {

    private List<AwardVO> min;
    private List<AwardVO> max;

}
