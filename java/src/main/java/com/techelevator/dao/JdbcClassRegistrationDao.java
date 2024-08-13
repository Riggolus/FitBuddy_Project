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
            String sql = "SELECT \n" +
                    "    o.session_date, \n" +
                    "    o.total_capacity, \n" +
                    "    COUNT(cr.registration_id) AS current_registration_count, \n" +
                    "    cs.class_name, \n" +
                    "    cs.description, \n" +
                    "    cs.class_id, \n" +
                    "    cr.registration_date,\n" +
                    "    (a.first_name || ' ' || a.last_name) AS instructor_name\n" +
                    "FROM \n" +
                    "    occupancy o\n" +
                    "JOIN \n" +
                    "    class_schedule cs ON o.class_id = cs.class_id\n" +
                    "LEFT JOIN \n" +
                    "    class_registration cr ON o.occupancy_id = cr.occupancy_id \n" +
                    "JOIN \n" +
                    "    account a ON cs.instructor_id = a.user_id\n" +
                    "WHERE \n" +
                    "    cs.class_name = ? \n" +
                    "    AND o.session_date = ?\n" +
                    "GROUP BY \n" +
                    "    o.session_date, o.total_capacity, cs.class_name, cs.description, cs.class_id, a.first_name, a.last_name, cr.registration_date;";
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
    public List<ClassRegistration>myClasses(Principal principal){
        List<ClassRegistration> classRegistrations =new ArrayList<>();
        ClassRegistration classRegistration = new ClassRegistration();
        User user = userDao.getUserByUsername(principal.getName());
        String sql = "SELECT \n" +
                "                     cr.registration_date,\n" +
                "                   o.session_date,\n" +
                "                    o.total_capacity,\n" +
                "                   COUNT(cr.registration_id) AS current_registration_count,\n" +
                "                    cs.class_name,\n" +
                "                    cs.description,\n" +
                "                    cs.class_id,\n" +
                "                    (a.first_name || ' ' || a.last_name) AS instructor_name\n" +
                "                    FROM \n" +
                "                    occupancy o\n" +
                "                    JOIN \n" +
                "                    class_schedule cs ON o.class_id = cs.class_id\n" +
                "                    LEFT JOIN \n" +
                "                    class_registration cr ON o.occupancy_id = cr.occupancy_id\n" +
                "                    JOIN \n" +
                "                    account a ON cs.instructor_id = a.user_id\n" +
                "                    WHERE\n" +
                "\t\t\t\t    cr.user_id = ?\n" +
                "            AND o.session_date >= CURRENT_TIMESTAMP\n " +
                "                    GROUP BY\n" +
                "                    o.session_date, o.total_capacity, cs.class_name, cs.description, cs.class_id, a.first_name, a.last_name, cr.user_id, cr.registration_date;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
            while (results.next()){
                classRegistration = mapRowsetToClassRegistration(results);
                classRegistrations.add(classRegistration);
            }
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        }
        return classRegistrations;
    }
    @Override
    public void registerForClass(ClassRegistration classRegistration, Principal principal) {
        User currentUser = userDao.getUserByUsername(principal.getName());
        String sql1 = "INSERT INTO occupancy (class_id, session_date, total_capacity, current_capacity)\n" +
                "VALUES (?,  ?, ?, ?);\n";
        String sql2= "\n" +
                "INSERT INTO class_registration(user_id, occupancy_id)\n" +
                "VALUES (?, (SELECT occupancy_id FROM occupancy ORDER BY occupancy_id DESC LIMIT 1)); ";
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
        classRegistration.setCurrentCapacity(rs.getInt("current_registration_count"));
        classRegistration.setClassName(rs.getString("class_name"));
        classRegistration.setDescription(rs.getString("description"));
        classRegistration.setRegistrationDate(rs.getDate("registration_date"));
        classRegistration.setInstructorName(rs.getString("instructor_name"));
        classRegistration.setClassId(rs.getInt("class_id"));
        return classRegistration;
    }
}
