package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CheckInCheckOut;
import com.techelevator.model.User;
import com.techelevator.model.UserStatus;
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
public class JdbcCheckInCheckOutDao implements CheckInCheckOutDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;


    public JdbcCheckInCheckOutDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }


    //CheckIn Method By User
    @Override
    public void checkIn(Principal principal) {
        Date dateTime = new Date();
        User user = userDao.getUserByUsername(principal.getName());
        String sql = "INSERT INTO check_in_check_out (user_id, check_in_time) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, user.getId(), dateTime);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }

    }
    @Override
    public List<UserStatus> getUserStatuses(){
        List<UserStatus> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, c.check_in_time, c.check_out_time FROM users u LEFT JOIN ( SELECT user_id, check_in_time, check_out_time FROM check_in_check_out WHERE (user_id, check_in_time) IN ( SELECT user_id, MAX(check_in_time) FROM check_in_check_out GROUP BY user_id ) ) c ON u.user_id = c.user_id ORDER BY u.user_id;";
//                "SELECT  u.user_id, u.username, MAX(c.check_in_time) AS check_in_time, MAX(c.check_out_time) AS check_out_time " +
//                "FROM users u LEFT JOIN check_in_check_out c ON u.user_id = c.user_id " +
//                "GROUP BY u.user_id, u.username ORDER BY user_id";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                UserStatus user = new UserStatus(results.getInt("user_id"), results.getString("username"), results.getDate("check_in_time"), results.getDate("check_out_time"));
                users.add(user);
            }
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        }

        return users;

    }


    @Override
    public CheckInCheckOut getCheckInTime(int userId) {
        String sql2 = "SELECT check_in_out_id, user_id, check_in_time, check_out_time FROM check_in_check_out WHERE user_id = ? AND check_out_time IS NULL";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql2, userId);
        CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
        if(results.next()) {
            checkInCheckOut.setUserId(results.getInt("user_id"));
            checkInCheckOut.setCheckInTime(results.getDate("check_in_time"));
            checkInCheckOut.setCheckOutTime(results.getDate("check_out_time"));
//            checkInCheckOut = mapRowToCheckInCheckOut(results);
        }
        return checkInCheckOut;

    }

    //Checkout Method By User
    @Override
    public void checkOut(Principal principal) {
        Date dateTime = new Date();
        User user = userDao.getUserByUsername(principal.getName());
        String sql = "UPDATE check_in_check_out SET check_out_time = ? WHERE user_id = ? AND check_out_time IS NULL";
        try {
            jdbcTemplate.update(sql, dateTime, user.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
    }
    @Override
    public void employeeCheckIn(int userId, Principal principal){
        Date dateTime = new Date();
        User employee = userDao.getUserByUsername(principal.getName());
        String sql = "INSERT INTO check_in_check_out (user_id, check_in_time, check_in_by) " +
                "VALUES (?,?,?);";
        try {
            jdbcTemplate.update(sql, userId, dateTime, employee.getId());
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        }
    }
    @Override
    public void employeeCheckOut(int userId, Principal principal){
        Date datetime = new Date();
        User employee = userDao.getUserByUsername(principal.getName());
        String sql = "UPDATE check_in_check_out SET check_out_time = ?, check_out_by = ? WHERE user_id = ? AND check_out_time IS NULL";
        try {
            jdbcTemplate.update(sql, datetime, employee.getId(), userId);
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Error", e);
        }
    }
    @Override
    public CheckInCheckOut myStatus(Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        String sql2 = "SELECT check_in_out_id, user_id, check_in_time, check_out_time FROM check_in_check_out WHERE user_id = ? AND check_out_time IS NULL";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql2, user.getId());
        CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
        if(results.next()) {
            checkInCheckOut.setUserId(results.getInt("user_id"));
            checkInCheckOut.setCheckInTime(results.getDate("check_in_time"));
            checkInCheckOut.setCheckOutTime(results.getDate("check_out_time"));
//            checkInCheckOut = mapRowToCheckInCheckOut(results);
        }
        return checkInCheckOut;

    }


//    private CheckInCheckOut mapRowToCheckInCheckOut(SqlRowSet rs) {
//        CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
//        checkInCheckOut.setCheckInCheckOutId(rs.getInt("check_in_out_id"));
//        checkInCheckOut.setUserId(rs.getInt("user_id"));
//        checkInCheckOut.setCheckInTime(rs.getDate("check_in_time"));
//        checkInCheckOut.setCheckOutTime(rs.getDate("check_out_time"));
//        checkInCheckOut.setCheckInBy(rs.getInt("check_in_by"));
//        checkInCheckOut.setCheckOutBy(rs.getInt("check_out_by"));
//        return checkInCheckOut;
//    }
}
