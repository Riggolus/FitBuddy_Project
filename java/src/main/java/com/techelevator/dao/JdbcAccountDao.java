package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Account;
import com.techelevator.model.AccountDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcAccountDao implements AccountDao{


    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account createAccount(AccountDto accountDto){

        String sql = "INSERT INTO account (user_id, username, email, first_name, last_name " +
                "profile_picture, profile, goals, created_at)" +
                "WHERE values (?,?,?,?,?,?,?,?,?)";
        try {
            int newAccountId = jdbcTemplate.queryForObject(sql, int.class, accountDto.getUserId(), accountDto.getUsername(),
                    accountDto.getEmail(), accountDto.getFirstName(), accountDto.getLastName(),
                    accountDto.getProfilePicture(), accountDto.getProfile(), accountDto.getGoals(), accountDto.getCreatedAt());
            if (accountDto != null) {
                sql = "INSERT INTO account (accountId) VALUES (?)";
                jdbcTemplate.update(sql, newAccountId);
            }
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
