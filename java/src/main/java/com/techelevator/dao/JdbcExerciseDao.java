package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Exercise;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcExerciseDao implements ExerciseDao{

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public JdbcExerciseDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Exercise> getExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM exercise";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()){
            Exercise exercise = new Exercise();
            exercise = mapToRowSet(results);
            exercises.add(exercise);
        }
        return exercises;
    }
    @Override
    public Exercise getExerciseById(int id){
        Exercise exercise = null;
        String sql = "SELECT * FROM exercise WHERE exercise_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()){
                exercise = mapToRowSet(results);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to Database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violated", e);
        }
        return exercise;
    }

    public Exercise mapToRowSet(SqlRowSet rs){
        Exercise exercise = new Exercise();
        exercise.setExerciseId(rs.getInt("exercise_id"));
        exercise.setExerciseName(rs.getString("exercise_name"));
        exercise.setExerciseDescription(rs.getString("exercise_description"));
        return exercise;
    }
}
