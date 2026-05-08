package com.petadopt.service.impl;

import com.petadopt.mapper.AdoptRecordMapper;
import com.petadopt.mapper.PetMapper;
import com.petadopt.mapper.UserMapper;
import com.petadopt.service.StatisticsService;
import com.petadopt.vo.StatisticsOverview;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final AdoptRecordMapper adoptRecordMapper;

    public StatisticsServiceImpl(UserMapper userMapper, PetMapper petMapper, AdoptRecordMapper adoptRecordMapper) {
        this.userMapper = userMapper;
        this.petMapper = petMapper;
        this.adoptRecordMapper = adoptRecordMapper;
    }

    @Override
    public StatisticsOverview getOverview() {
        StatisticsOverview overview = new StatisticsOverview();
        overview.setUserCount(userMapper.countAll());
        overview.setPetCount(petMapper.countAll());
        overview.setAdoptionCount(adoptRecordMapper.countAll());
        overview.setWaitingCount(adoptRecordMapper.countWaiting());
        return overview;
    }
}
