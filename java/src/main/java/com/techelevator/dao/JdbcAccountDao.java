package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Account;
import com.techelevator.model.AccountDto;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JdbcAccountDao implements AccountDao{


    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserDao jdbcUserDao;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao){
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
    }

    @Override
    public Account createAccount(AccountDto accountDto, Principal principal){

        User user = jdbcUserDao.getUserByUsername(principal.getName());
//       LocalDateTime date = LocalDateTime.now();

        String sql = "INSERT INTO account (user_id, username, email, first_name, last_name, " +
                "profile_picture, profile, goals) " +
                " values (?,?,?,?,?,?,?,?)";
        try {
             jdbcTemplate.update(sql, user.getId(), user.getUsername(),
                    accountDto.getEmail(), accountDto.getFirstName(), accountDto.getLastName(),
                    accountDto.getProfilePicture(), accountDto.getProfile(), accountDto.getGoals());
//            if (accountDto != null) {
//                sql = "INSERT INTO account (accountId) VALUES (?)";
//                jdbcTemplate.update(sql, newAccountId);

        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity violation", e);
        }
        sql  = "SELECT * FROM account ORDER BY account_id DESC LIMIT 1";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        Account account = null;
        if(results.next()){
            account = mapRowToAccount(results);
        }

        return account;
    }

    @Override
    public Account accountByAccountId(int accountId) {
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

            if (results.next()) {
                return mapRowToAccount(results);
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
    }

    @Override
    public Account accountByUserId(int userId) {
        try {
            String sql = "SELECT * FROM account WHERE user_id = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                return mapRowToAccount(results);
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
    }

    @Override
    public Account accountByUsername(String username) {
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                return mapRowToAccount(results);
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
    }

    @Override
    public Account accountByEmail(String email) {
        try {
            String sql = "SELECT * FROM account WHERE email = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, email);
            if (results.next()) {
                return mapRowToAccount(results);
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
    }
    @Override
    public boolean editAccount(AccountDto accountDto, Principal principal){
        int rowsAffected = 0;
        User user = jdbcUserDao.getUserByUsername(principal.getName());
        String sql = "UPDATE account SET email = ?, first_name = ?, last_name = ?, profile_picture = ?, profile = ?, goals = ? WHERE user_id = ?";
        try {
            rowsAffected = jdbcTemplate.update(sql, accountDto.getEmail(), accountDto.getFirstName(), accountDto.getLastName(), accountDto.getProfilePicture(), accountDto.getProfile(), accountDto.getGoals(), user.getId());
            if (rowsAffected != 0) {
                return true;
            } else
                return false;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("cannot connect to database",e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Violation of data integrity", e);
        }

    }

    private Account mapRowToAccount(SqlRowSet rs){
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setUsername(rs.getString("username"));
        account.setEmail(rs.getString("email"));
        account.setFirstName(rs.getString("first_name"));
        account.setLastName(rs.getString("last_name"));
        account.setProfilePicture(rs.getString("profile_picture"));
        account.setProfile(rs.getString("profile"));
        account.setGoals(rs.getString("goals"));
        account.setCreatedAt(rs.getDate("created_at"));
        return account;

    }
}
