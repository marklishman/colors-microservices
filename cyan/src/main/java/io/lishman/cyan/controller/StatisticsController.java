package io.lishman.cyan.controller;

import io.lishman.cyan.model.Statistics;
import io.lishman.cyan.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
class StatisticsController {

    final StatisticsService statisticsService;

    StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    ResponseEntity<Statistics> getStata() {
        final Statistics statistics = statisticsService.getStats();
        return ResponseEntity.ok(statistics);
    }

}
