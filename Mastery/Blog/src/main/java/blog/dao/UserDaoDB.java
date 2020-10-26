package blog.dao;

import blog.dao.RoleDaoDB.RoleMapper;
import blog.dto.Role;
import blog.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoDB implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public User getUserById(int id) {
        try {
            final String SELECT_USER_BY_ID = "SELECT * FROM USER WHERE USERID = ?";
            User user = jdbc.queryForObject(SELECT_USER_BY_ID, new UserMapper(), id);
            user.setRoles(getRolesForUser(user.getId()));
            return user;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            final String SELECT_USER_BY_USERNAME = "SELECT * FROM USER WHERE USERNAME = ?";
            User user = jdbc.queryForObject(SELECT_USER_BY_USERNAME, new UserMapper(), username);
            user.setRoles(getRolesForUser(user.getId()));
            return user;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public User getActiveUserByUsername(String username) {
        try {
            final String SELECT_USER_BY_USERNAME = "SELECT * FROM USER WHERE USERNAME = ? AND ENABLED = 1";
            User user = jdbc.queryForObject(SELECT_USER_BY_USERNAME, new UserMapper(), username);
            user.setRoles(getRolesForUser(user.getId()));
            return user;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        final String SELECT_ALL_USERS = "SELECT * FROM USER";
        List<User> users = jdbc.query(SELECT_ALL_USERS, new UserMapper());
        for (User user : users) {
            user.setRoles(getRolesForUser(user.getId()));
        }
        return users;
    }

    private Set<Role> getRolesForUser(int id) throws DataAccessException {
        final String SELECT_ROLES_FOR_USER = "SELECT r.* FROM USER_ROLE ur "
                + "JOIN role r ON ur.ROLEID = r.ROLEID "
                + "WHERE ur.USERID = ?";
        Set<Role> roles = new HashSet(jdbc.query(SELECT_ROLES_FOR_USER, new RoleMapper(), id));
        return roles;
    }

    @Override
    public void updateUser(User user) {
        final String UPDATE_USER = "UPDATE USER SET USERNAME = ?, PASSWORD = ?,ENABLED = ? WHERE USERID = ?";
        jdbc.update(UPDATE_USER, user.getUsername(), user.getPassword(), user.isEnabled(), user.getId());

        final String DELETE_USER_ROLE = "DELETE FROM USER_ROLE WHERE USERID = ?";
        jdbc.update(DELETE_USER_ROLE, user.getId());
        for (Role role : user.getRoles()) {
            final String INSERT_USER_ROLE = "INSERT INTO USER_ROLE(USERID, ROLEID) VALUES(?,?)";
            jdbc.update(INSERT_USER_ROLE, user.getId(), role.getId());
        }
    }

    @Override
    public void deleteUser(int id) {
        final String DELETE_USER_ROLE = "DELETE FROM USER_ROLE WHERE USERID = ?";
        final String DELETE_USER = "DELETE FROM USER WHERE USERID = ?";
        jdbc.update(DELETE_USER_ROLE, id);
        jdbc.update(DELETE_USER, id);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        User tempUser = getUserByUsername(user.getUsername());
        if (tempUser == null) {
            final String INSERT_USER = "INSERT INTO USER(USERNAME, PASSWORD, ENABLED) VALUES(?,?,?)";
            jdbc.update(INSERT_USER, user.getUsername(), user.getPassword(), user.isEnabled());
            int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
            user.setId(newId);

            if (user.getRoles() != null) {
                for (Role role : user.getRoles()) {
                    final String INSERT_USER_ROLE = "INSERT INTO USER_ROLE(USERID, ROLEID) VALUES(?,?)";
                    jdbc.update(INSERT_USER_ROLE, user.getId(), role.getId());
                }
            } else {
                user.setRoles(new HashSet<>());
            }
            return user;
        }
        return tempUser;
    }

    public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("USERID"));
            user.setUsername(rs.getString("USERNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setEnabled(rs.getBoolean("ENABLED"));
            return user;
        }
    }
}
