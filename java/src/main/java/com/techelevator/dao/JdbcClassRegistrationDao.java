package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.ClassDto;
import com.techelevator.model.ClassRegistration;
import com.techelevator.model.User;
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
public class JdbcClassRegistrationDao implements ClassRegistrationDao{


    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    public JdbcClassRegistrationDao(JdbcTemplate jdbcTemplate, UserDao userDao){
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public ClassRegistration displayRegistration (Date date, String className){
            ClassRegistration classregistration = new ClassRegistration();
            String sql = "SELECT o.session_date, o.total_capacity, o.current_capacity, cs.class_name, cs.description, cs.class_id, cr.registration_date, (a.first_name || ' ' || a.last_name) AS instructor_name\n" +
                    "FROM occupancy o\n" +
                    "JOIN class_schedule cs ON o.class_id = cs.class_id\n" +
                    "JOIN class_registration cr ON o.occupancy_id = cr.occupancy_id\n" +
                    "JOIN account a on cs.instructor_id = a.user_id\n" +
                    "WHERE cs.class_name = ?; AND session_date = ?";
            try{
                SqlRowSet results = jdbcTemplate.queryForRowSet(sql, className, date);
                if(results.next()){
                    classregistration = mapRowsetToClassRegistration(results);

                }
            }catch(CannotGetJdbcConnectionException e){
                throw new DaoException("Cannot connect to database", e);
            }catch (DataIntegrityViolationException e){
                throw new DaoException("Data Integrity Violation", e);
            }

        return classregistration;
    }

    @Override
    public void registerForClass(ClassRegistration classRegistration, Principal principal) {
        User currentUser = userDao.getUserByUsername(principal.getName());
        String sql1 = "INSERT INTO occupancy (class_id, session_date, total_capacity, current_capacity)\n" +
                "VALUES (?,  ?, ?, ?);\n";
        String sql2= "\n" +
                "INSERT INTO class_registration(user_id, occupancy_id)\n" +
                "VALUES (?, (SELECT occupancy_id FROM occupancy ORDER BY occupancy_id LIMIT 1)); ";
        try{
            jdbcTemplate.update(sql1, classRegistration.getClassId(), classRegistration.getSessionDate(), classRegistration.getTotalCapacity(), classRegistration.getCurrentCapacity());
            jdbcTemplate.update(sql2, currentUser.getId());
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        }
    }

    public ClassRegistration mapRowsetToClassRegistration (SqlRowSet rs){
        ClassRegistration classRegistration = new ClassRegistration();
        classRegistration.setSessionDate(rs.getDate("session_date"));
        classRegistration.setTotalCapacity(rs.getInt("total_capacity"));
        classRegistration.setCurrentCapacity(rs.getInt("current_capacity"));
        classRegistration.setClassName(rs.getString("class_name"));
        classRegistration.setDescription(rs.getString("description"));
        classRegistration.setRegistrationDate(rs.getDate("registration_date"));
        classRegistration.setInstructorName(rs.getString("instructor_name"));
        return classRegistration;
    }
}
