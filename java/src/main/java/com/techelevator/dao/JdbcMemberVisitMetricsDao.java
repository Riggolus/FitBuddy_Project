package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.MemberVisitMetrics;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMemberVisitMetricsDao implements MemberVisitMetricsDao {

    private final JdbcTemplate jdbcTemplate;

    private final UserDao userDao;

    @Autowired
    public JdbcMemberVisitMetricsDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public List<MemberVisitMetrics> memberVisitMetrics(Principal principal) {
        List<MemberVisitMetrics> memberVisitMetrics = new ArrayList<>();
        User user = userDao.getUserByUsername(principal.getName());
        String sql = "SELECT \n" +
                "\tc.user_id,\n" +
                "\tc.check_in_time::date AS date_visited_gym,\n" +
                "\tSUM(c.check_out_time - c.check_in_time) AS total_minutes_spent,\n" +
                "\tAVG(c.check_out_time - c.check_in_time) AS average_time_spent\n" +
                "FROM \n" +
                "\tcheck_in_check_out c\n" +
                "WHERE \n" +
                "\tc.user_id = ?\n" +
                "GROUP BY\n" +
                "\tc.user_id, date_visited_gym\n" +
                "ORDER BY\n" +
                "\tdate_visited_gym ASC;";
        try {

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
            while(results.next()) {
                MemberVisitMetrics memberVisitMetric = mapRowToMemberMetrics(results);
                memberVisitMetrics.add(memberVisitMetric);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
        return memberVisitMetrics;
    }

    @Override
    public List<MemberVisitMetrics> employeeViewMemberVisitMetrics(int userId) {
        List<MemberVisitMetrics> employeeViewMemberVisitMetrics = new ArrayList<>();
        String sql = "SELECT \n" +
                "\tc.user_id,\n" +
                "\tc.check_in_time::date AS date_visited_gym,\n" +
                "\tSUM(c.check_out_time - c.check_in_time) AS total_minutes_spent,\n" +
                "\tAVG(c.check_out_time - c.check_in_time) AS average_time_spent\n" +
                "FROM \n" +
                "\tcheck_in_check_out c\n" +
                "WHERE \n" +
                "\tc.user_id = ?\n" +
                "GROUP BY\n" +
                "\tc.user_id, date_visited_gym\n" +
                "ORDER BY\n" +
                "\tdate_visited_gym ASC;";
        try {
           SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
           while(results.next()) {
               MemberVisitMetrics employeeViewMemberVisitMetric = mapRowToMemberMetrics(results);
               employeeViewMemberVisitMetrics.add(employeeViewMemberVisitMetric);
           }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
        return employeeViewMemberVisitMetrics;
    }

    public MemberVisitMetrics mapRowToMemberMetrics(SqlRowSet rs) {
        MemberVisitMetrics memberVisitMetrics = new MemberVisitMetrics();
        memberVisitMetrics.setUserId(rs.getInt("user_id"));
        memberVisitMetrics.setDateVisitedGym(rs.getDate("date_visited_gym"));
        String totalMinutesSpent = rs.getString("total_minutes_spent");
        Duration time = parseCustomInterval(totalMinutesSpent);
        memberVisitMetrics.setTotalMinutesSpent(time);
        String averageTimeSpent = rs.getString("average_time_spent");
        Duration time2 = parseCustomInterval(averageTimeSpent);
        memberVisitMetrics.setAverageTimeSpent(time2);
        return memberVisitMetrics;
    }

    public static Duration parseCustomInterval(String interval) {
        String[] parts = interval.split(" ");

        int years = Integer.parseInt(parts[0]);
        int months = Integer.parseInt(parts[2]);
        int days = Integer.parseInt(parts[4]);
        int hours = Integer.parseInt(parts[6]);
        int minutes = Integer.parseInt(parts[8]);
        double seconds = Double.parseDouble(parts[10]);

        // Convert years, months, days into hours or seconds
        // This is a simplification, as it assumes 365 days/year and 30 days/month
        long totalSeconds = (long) (years * 365L * 24L * 3600L +
                months * 30L * 24L * 3600L +
                days * 24L * 3600L +
                hours * 3600L +
                minutes * 60L +
                seconds);

        return Duration.ofSeconds(totalSeconds);
    }
}
