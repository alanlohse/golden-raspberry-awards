package com.anlohse.raspberry_awards.vo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AwardVO {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

}
