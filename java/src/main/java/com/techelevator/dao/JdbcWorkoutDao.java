package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Exercise;
import com.techelevator.model.User;
import com.techelevator.model.Workout;
import com.techelevator.model.WorkoutDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JdbcWorkoutDao implements WorkoutDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    public JdbcWorkoutDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public void addWorkout(WorkoutDto workoutDto, Principal principal) {
//        String exerciseByIdSql = "SELECT exercise_id FROM exercise WHERE exercise_name = ?";
//        Integer exerciseId = jdbcTemplate.queryForObject(exerciseByIdSql, Integer.class, exercise.getExerciseName());
        User username = userDao.getUserByUsername(principal.getName());
        String sql = "INSERT INTO workout (user_id, exercise_id, sets, reps, weight, duration, date_of_workout) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Date date = new Date();
        try{
            jdbcTemplate.update(sql, username.getId(), workoutDto.getExerciseId(), workoutDto.getSets(), workoutDto.getReps(), workoutDto.getWeight(), workoutDto.getDuration(), date);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
    }

    @Override
    public Workout getLastWorkoutByUserId(int userId) {
        String sql = "SELECT * FROM workout WHERE user_id = ? ORDER BY workout_id DESC LIMIT 1";
        Workout workout = null;
        try {
           SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, userId);
           if(results.next()) {
               workout = mapToRowSet(results);
           }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
        return workout;
    }

    @Override
    public List<WorkoutDto> getWorkoutByDate(Date dateOfWorkout, Principal principal) {
        List<WorkoutDto> workouts = new ArrayList<>();
        User user = userDao.getUserByUsername(principal.getName());
        String sql = "SELECT w.user_id, e.exercise_id, sets, reps, weight, duration, date_of_workout, exercise_name FROM workout w JOIN exercise e ON w.exercise_id = e.exercise_id WHERE date_of_workout::date = ? AND user_id = ?";

        try {
            SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, dateOfWorkout, user.getId());
            while (results.next()) {
                WorkoutDto workout = new WorkoutDto();
                workout.setUserId(results.getInt("user_id"));
                workout.setExerciseId(results.getInt("exercise_id"));
                workout.setSets(results.getInt("sets"));
                workout.setReps(results.getInt("reps"));
                workout.setWeight(results.getInt("weight"));
                workout.setDuration(results.getInt("duration"));
                workout.setDateOfWorkout(results.getDate("date_of_workout"));
                workout.setExerciseName(results.getString("exercise_name"));
                workouts.add(workout);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
        return workouts;

    }

    @Override
    public List<WorkoutDto> getUserWorkoutByDate(Date dateOfWorkout, int userId) {
        List<WorkoutDto> workouts = new ArrayList<>();

        String sql = "SELECT w.user_id, e.exercise_id, sets, reps, weight, duration, date_of_workout, exercise_name FROM workout w JOIN exercise e ON w.exercise_id = e.exercise_id WHERE date_of_workout::date = ? AND user_id = ?";

        try {
            SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, dateOfWorkout, userId);
            while (results.next()) {
                WorkoutDto workout = new WorkoutDto();
                workout.setUserId(results.getInt("user_id"));
                workout.setExerciseId(results.getInt("exercise_id"));
                workout.setSets(results.getInt("sets"));
                workout.setReps(results.getInt("reps"));
                workout.setWeight(results.getInt("weight"));
                workout.setDuration(results.getInt("duration"));
                workout.setDateOfWorkout(results.getDate("date_of_workout"));
                workout.setExerciseName(results.getString("exercise_name"));
                workouts.add(workout);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
        return workouts;

    }

    @Override
    public List<Workout> getAllWorkouts(int userId) {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workout WHERE user_id = ? ORDER BY workout_id";

        try{
            SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Workout workout = new Workout();
                workout = mapToRowSet(results);
                workouts.add(workout);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
        return workouts;
    }

    public Workout mapToRowSet(SqlRowSet rs) {
        Workout workout = new Workout();
        workout.setWorkoutId(rs.getInt("workout_id"));
        workout.setUserId(rs.getInt("user_id"));
        workout.setExerciseId(rs.getInt("exercise_id"));
        workout.setSets(rs.getInt("sets"));
        workout.setReps(rs.getInt("reps"));
        workout.setWeight(rs.getDouble("weight"));
        workout.setDuration(rs.getInt("duration"));
        workout.setDateOfWorkout(rs.getDate("date_of_workout"));
        return workout;
    }

}
