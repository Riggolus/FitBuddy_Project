package com.techelevator.controller;

import com.techelevator.dao.MonthlyEquipmentUsageDao;
import com.techelevator.model.MonthlyEquipmentUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/monthly-equipment-usage")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
public class MonthlyEquipmentUsageController {

    private final MonthlyEquipmentUsageDao monthlyEquipmentUsageDao;

    @Autowired
    public MonthlyEquipmentUsageController(MonthlyEquipmentUsageDao monthlyEquipmentUsageDao) {
        this.monthlyEquipmentUsageDao = monthlyEquipmentUsageDao;
    }

    @GetMapping("/specific-month/{date}")
    public List<MonthlyEquipmentUsage> specificMonthlyEquipmentUsage(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Set the calendar to the first day of the month
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        // Set the calendar to the last day of the month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        // Call the DAO method with the start and end date
        return monthlyEquipmentUsageDao.specificMonthlyEquipmentUsage(startDate, endDate);
    }

    @GetMapping("/current-month")
    public List<MonthlyEquipmentUsage> monthlyEquipmentUsages() {
        return monthlyEquipmentUsageDao.currentMonthlyEquipmentUsage();
    }

}
