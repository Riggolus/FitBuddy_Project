package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.User;
import com.techelevator.model.Workout;
import com.techelevator.model.WorkoutMetrics;
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
public class JdbcWorkoutMetricDao implements WorkoutMetricDao{

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userdao;

    JdbcWorkoutMetricDao(JdbcTemplate jdbcTemplate, UserDao userdao){
        this.jdbcTemplate = jdbcTemplate;
        this.userdao = userdao;
    }

    @Override
    public List<WorkoutMetrics> myMetrics(Principal principal){
        User currentUser = userdao.getUserByUsername(principal.getName());
        List<WorkoutMetrics> metrics = new ArrayList<>();
        String sql =  "WITH unique_check_in_out AS (\n" +
                "    SELECT\n" +
                "        user_id,\n" +
                "        date_trunc('day', check_in_time) AS workout_date,\n" +
                "        MIN(check_in_time) AS first_check_in_time,\n" +
                "        MAX(check_out_time) AS last_check_out_time\n" +
                "    FROM\n" +
                "        check_in_check_out\n" +
                "    GROUP BY\n" +
                "        user_id,\n" +
                "        date_trunc('day', check_in_time)\n" +
                ")\n" +
                "SELECT\n" +
                "    w.user_id,\n" +
                "    e.exercise_name,\n" +
                "    w.sets,\n" +
                "    w.reps,\n" +
                "    w.weight,\n" +
                "    w.date_of_workout,\n" +
                "    (uci.last_check_out_time - uci.first_check_in_time) AS time_spent\n" +
                "FROM\n" +
                "    workout w\n" +
                "JOIN\n" +
                "    exercise e ON w.exercise_id = e.exercise_id\n" +
                "JOIN\n" +
                "    unique_check_in_out uci ON w.user_id = uci.user_id\n" +
                "                           AND w.date_of_workout = uci.workout_date\n" +
                "WHERE\n" +
                "    w.user_id = ?\n" +
                "ORDER BY\n" +
                "    w.date_of_workout ASC;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, currentUser.getId());

            while(results.next()){
                WorkoutMetrics workoutMetrics = mapRowToMetrics(results);
                metrics.add(workoutMetrics);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("cannot connect to database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }


        return metrics;
    }

    @Override
    public List<WorkoutMetrics> memberMetrics(int userId){

        List<WorkoutMetrics> metrics = new ArrayList<>();
        String sql =  "WITH unique_check_in_out AS (\n" +
                "    SELECT\n" +
                "        user_id,\n" +
                "        date_trunc('day', check_in_time) AS workout_date,\n" +
                "        MIN(check_in_time) AS first_check_in_time,\n" +
                "        MAX(check_out_time) AS last_check_out_time\n" +
                "    FROM\n" +
                "        check_in_check_out\n" +
                "    GROUP BY\n" +
                "        user_id,\n" +
                "        date_trunc('day', check_in_time)\n" +
                ")\n" +
                "SELECT\n" +
                "    w.user_id,\n" +
                "    e.exercise_name,\n" +
                "    w.sets,\n" +
                "    w.reps,\n" +
                "    w.weight,\n" +
                "    w.date_of_workout,\n" +
                "    (uci.last_check_out_time - uci.first_check_in_time) AS time_spent\n" +
                "FROM\n" +
                "    workout w\n" +
                "JOIN\n" +
                "    exercise e ON w.exercise_id = e.exercise_id\n" +
                "JOIN\n" +
                "    unique_check_in_out uci ON w.user_id = uci.user_id\n" +
                "                           AND w.date_of_workout = uci.workout_date\n" +
                "WHERE\n" +
                "    w.user_id = ?\n" +
                "ORDER BY\n" +
                "    w.date_of_workout ASC;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

            while(results.next()){
                WorkoutMetrics workoutMetrics = mapRowToMetrics(results);
                metrics.add(workoutMetrics);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("cannot connect to database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }


        return metrics;
    }



    public WorkoutMetrics mapRowToMetrics(SqlRowSet rs){
        WorkoutMetrics metrics = new WorkoutMetrics();
        metrics.setUserId(rs.getInt("user_id"));
        metrics.setExerciseName(rs.getString("exercise_name"));
        metrics.setSets(rs.getInt("sets"));
        metrics.setReps(rs.getInt("reps"));
        metrics.setWeight(rs.getInt("weight"));
        metrics.setDate(rs.getDate("date_of_workout"));
        String timespent = rs.getString("time_spent");
        Duration time = parseCustomInterval(timespent);
        metrics.setDuration(time);
        return metrics;
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
