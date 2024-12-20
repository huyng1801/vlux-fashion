package vn.student.vluxfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.student.vluxfashion.service.StatisticService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    // Get statistics for today
    @GetMapping("/today")
    public Map<String, Object> getStatisticsToday() {
        return statisticService.getStatisticsToday();
    }

    // Get statistics for the current month
    @GetMapping("/this-month")
    public Map<String, Object> getStatisticsThisMonth() {
        return statisticService.getStatisticsThisMonth();
    }

    // Get statistics for the current year
    @GetMapping("/this-year")
    public Map<String, Object> getStatisticsThisYear() {
        return statisticService.getStatisticsThisYear();
    }

    // Get item quantity by category for the current year
    @GetMapping("/item-quantity-by-category")
    public Map<String, Integer> getItemQuantityByCategory() {
        return statisticService.getItemQuantityByCategory();
    }

    // Get monthly statistics for the current year
    @GetMapping("/monthly")
    public List<Map<String, Object>> getMonthlyStatisticsForYear() {
        return statisticService.getMonthlyStatisticsForCurrentYear();
    }

    // Get daily statistics for a specific month
    @GetMapping("/daily")
    public List<Map<String, Object>> getDailyStatisticsForMonth(@RequestParam("year") int year, @RequestParam("month") int month) {
        return statisticService.getDailyStatisticsForMonth(year, month);
    }

    // Get statistics for a specific day
    @GetMapping("/by-day")
    public Map<String, Object> getStatisticsByDay(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day) {
        return statisticService.getStatisticsByDay(year, month, day);
    }
}
