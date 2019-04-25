package io.lishman.cyan.controller;

import io.lishman.cyan.model.Statistics;
import io.lishman.cyan.service.WebClientStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
class StatisticsController {

    final WebClientStatisticsService webClientStatisticsService;

    StatisticsController(final WebClientStatisticsService webClientStatisticsService) {
        this.webClientStatisticsService = webClientStatisticsService;
    }

    @GetMapping
    ResponseEntity<Statistics> getStats() {
        final Statistics statistics = webClientStatisticsService.getStats();
        return ResponseEntity.ok(statistics);
    }

}
