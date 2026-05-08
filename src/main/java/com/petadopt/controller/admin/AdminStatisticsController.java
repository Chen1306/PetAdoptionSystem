package com.petadopt.controller.admin;

import com.petadopt.common.ApiResponse;
import com.petadopt.service.StatisticsService;
import com.petadopt.util.AuthContext;
import com.petadopt.vo.StatisticsOverview;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin/statistics")
public class AdminStatisticsController {

    private final StatisticsService statisticsService;

    public AdminStatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/overview")
    public ApiResponse<StatisticsOverview> overview(HttpServletRequest request) {
        AuthContext.getAdmin(request);
        return ApiResponse.success(statisticsService.getOverview());
    }
}
