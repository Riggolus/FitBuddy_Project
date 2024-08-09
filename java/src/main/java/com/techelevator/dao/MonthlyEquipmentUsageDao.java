package com.techelevator.dao;

import com.techelevator.model.MonthlyEquipmentUsage;

import java.time.Month;
import java.util.Date;
import java.util.List;

public interface MonthlyEquipmentUsageDao {

    List<MonthlyEquipmentUsage> currentMonthlyEquipmentUsage();

    List<MonthlyEquipmentUsage> specificMonthlyEquipmentUsage(Date date, Date date2);
}
