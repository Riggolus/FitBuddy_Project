package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.ClassSchedule;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcClassScheduleDao implements ClassScheduleDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcClassScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ClassSchedule> getAllClasses() {
        List<ClassSchedule> getClasses = new ArrayList<>();
        String sql = "SELECT\n" +
                "class_name, (a.first_name || ' ' || a.last_name) AS instructor_name,\n" +
                "start_time, end_time, description, is_monday, is_tuesday, is_wednesday ,is_thursday,\n" +
                "is_friday, is_saturday, is_sunday, recurrence_end, created_by, cs.created_at\n" +
                "FROM class_schedule cs\n" +
                "JOIN account a ON cs.instructor_id = a.user_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                ClassSchedule classSchedule = mapToClassSchedule(results);
                getClasses.add(classSchedule);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to database");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation");
        }
        return getClasses;

    }

    public ClassSchedule mapToClassSchedule(SqlRowSet rs) {
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassName(rs.getString("class_name"));
        classSchedule.setInstructorName(rs.getString("instructor_name"));
        classSchedule.setStartTime(rs.getTime("start_time").toLocalTime());
        classSchedule.setEndTime(rs.getTime("end_time").toLocalTime());
        classSchedule.setDescription(rs.getString("description"));
        classSchedule.setMonday(rs.getBoolean("is_monday"));
        classSchedule.setTuesday(rs.getBoolean("is_tuesday"));
        classSchedule.setWednesday(rs.getBoolean("is_wednesday"));
        classSchedule.setThursday(rs.getBoolean("is_thursday"));
        classSchedule.setFriday(rs.getBoolean("is_friday"));
        classSchedule.setSaturday(rs.getBoolean("is_saturday"));
        classSchedule.setSunday(rs.getBoolean("is_sunday"));
        classSchedule.setRecurrenceEnd(rs.getDate("recurrence_end"));
        classSchedule.setCreatedBy(rs.getInt("created_by"));
        classSchedule.setCreatedAt(rs.getDate("created_at"));
        return classSchedule;
    }

}
