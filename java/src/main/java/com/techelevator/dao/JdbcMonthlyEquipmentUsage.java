package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.MonthlyEquipmentUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JdbcMonthlyEquipmentUsage implements MonthlyEquipmentUsageDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMonthlyEquipmentUsage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MonthlyEquipmentUsage> currentMonthlyEquipmentUsage() {
        List<MonthlyEquipmentUsage> monthlyEquipmentUsages = new ArrayList<>();
        String sql = "SELECT\n" +
                "    e.exercise_name,\n" +
                "    SUM(w.sets) AS usage_count,\n" +
                "\t(w.sets * w.reps * w.weight) as total_load_volume,\n" +
                "\tw.reps,\n" +
                "    SUM(w.duration) AS total_duration,\n" +
                "\tw.weight\n" +
                "FROM\n" +
                "    workout w\n" +
                "JOIN\n" +
                "    exercise e ON w.exercise_id = e.exercise_id\n" +
                "WHERE\n" +
                "    DATE_TRUNC('month', w.date_of_workout) = DATE_TRUNC('month', CURRENT_DATE)\n" +
                "GROUP BY\n" +
                "    e.exercise_name, w.weight, w.sets, w.reps\n" +
                "ORDER BY\n" +
                "    usage_count DESC;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while(results.next()) {
                MonthlyEquipmentUsage monthlyEquipmentUsage = mapRowToEquipmentUsage(results);
                monthlyEquipmentUsages.add(monthlyEquipmentUsage);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
        return monthlyEquipmentUsages;
    }

    @Override
    public List<MonthlyEquipmentUsage> specificMonthlyEquipmentUsage(Date date, Date date2) {
        List<MonthlyEquipmentUsage> monthlyEquipmentUsages = new ArrayList<>();
        String sql = "SELECT\n" +
                "    e.exercise_name,\n" +
                "    SUM(w.sets) AS usage_count,\n" +
                "\t(w.sets * w.reps * w.weight) as total_load_volume,\n" +
                "\tw.reps,\n" +
                "    SUM(w.duration) AS total_duration,\n" +
                "\tw.weight\n" +
                "FROM\n" +
                "    workout w\n" +
                "JOIN\n" +
                "    exercise e ON w.exercise_id = e.exercise_id\n" +
                "WHERE\n" +
                "    w.date_of_workout >= ? AND w.date_of_workout <= ?  -- Filter by the current month\n" +
                "GROUP BY\n" +
                "    e.exercise_name, w.weight, w.sets, w.reps\n" +
                "ORDER BY\n" +
                "    usage_count DESC;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, date, date2);

            while(results.next()) {
                MonthlyEquipmentUsage monthlyEquipmentUsage = mapRowToEquipmentUsage(results);
                monthlyEquipmentUsages.add(monthlyEquipmentUsage);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
        return monthlyEquipmentUsages;
    }

    public MonthlyEquipmentUsage mapRowToEquipmentUsage(SqlRowSet rs) {
        MonthlyEquipmentUsage monthlyEquipmentUsage = new MonthlyEquipmentUsage();
        monthlyEquipmentUsage.setExerciseName(rs.getString("exercise_name"));
        monthlyEquipmentUsage.setUsageCount(rs.getInt("usage_count"));
        monthlyEquipmentUsage.setTotalLoadVolume(rs.getDouble("total_load_volume"));
        monthlyEquipmentUsage.setReps(rs.getInt("reps"));
        monthlyEquipmentUsage.setTotalDuration(rs.getInt("total_duration"));
        monthlyEquipmentUsage.setWeight(rs.getDouble("weight"));
        return monthlyEquipmentUsage;
    }

}
