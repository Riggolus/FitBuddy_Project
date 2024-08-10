package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Achievements;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAchievementDao implements AchievementDao {

    private final JdbcTemplate jdbcTemplate;

    private final UserDao userDao;

    public JdbcAchievementDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public List<Achievements> getAchievements(Principal principal) {
        List<Achievements> achievements = new ArrayList<>();
        User user = userDao.getUserByUsername(principal.getName());
        String sql = "SELECT * FROM achievements WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
            while (results.next()) {
                Achievements achievement = mapToRow(results);
                achievements.add(achievement);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity violation", e);
        }
        return achievements;
    }

    @Override
    public Achievements getAchievementById(int goalId, Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        String sql = "SELECT * FROM achievements WHERE goal_id = ? AND user_id = ?;";
        Achievements achievement = new Achievements();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, goalId, user.getId());
            if (results.next()){
                achievement = mapToRow(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity violation", e);
        }
        return achievement;
    }

    @Override
    public void createAchievement(Achievements achievements, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        String sql = "INSERT INTO achievements (user_id, goal_description, completed) " +
                "VALUES (?, ?, ?);";
        try {
            jdbcTemplate.update(sql, user.getId(), achievements.getGoalDescription(), achievements.isCompleted());
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity violation", e);
        }
    }

    @Override
    public boolean updateAchievementById(Achievements achievements, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        boolean status = false;
        String sql = "UPDATE achievements SET completed = ? " +
                "WHERE user_id = ? AND goal_id = ?;";
        try {
            int id = jdbcTemplate.update(sql, achievements.isCompleted(), user.getId(), achievements.getGoalId());
            if (id > 0) {
                status = true;
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity violation", e);
        }
        return status;
    }

    @Override
    public boolean deleteAchievementById(int goalId) {
        boolean status = false;
        String sql = "DELETE FROM achievements WHERE goal_id = ?;";
        try {
          int id = jdbcTemplate.update(sql, goalId);
          if(id > 0) {
              status = true;
          }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity violation", e);
        }
        return status;
    }



    public Achievements mapToRow(SqlRowSet sqlRowSet){
        Achievements achievement = new Achievements();
        achievement.setGoalId(sqlRowSet.getInt("goal_id"));
        achievement.setUserId(sqlRowSet.getInt("user_id"));
        achievement.setGoalDescription(sqlRowSet.getString("goal_description"));
        achievement.setCompleted(sqlRowSet.getBoolean("completed"));
        return achievement;
    }
}
