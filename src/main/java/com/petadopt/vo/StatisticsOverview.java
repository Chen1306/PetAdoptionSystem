package com.petadopt.vo;

import lombok.Data;

@Data
public class StatisticsOverview {

    private Long userCount;
    private Long petCount;
    private Long adoptionCount;
    private Long waitingCount;
}
