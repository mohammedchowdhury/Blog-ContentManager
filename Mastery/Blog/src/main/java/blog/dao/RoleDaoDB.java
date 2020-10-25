package blog.dao;

import blog.dto.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoleDaoDB implements RoleDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Role getRoleById(int id) {
        try {
            final String SELECT_ROLE_BY_ID = "SELECT * FROM ROLE WHERE ROLEID = ?";
            return jdbc.queryForObject(SELECT_ROLE_BY_ID, new RoleMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Role getRoleByRole(String role) {
        try {
            final String SELECT_ROLE_BY_ROLE = "SELECT * FROM ROLE WHERE ROLE = ?";
            return jdbc.queryForObject(SELECT_ROLE_BY_ROLE, new RoleMapper(), role);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Role> getAllRoles() {
        final String SELECT_ALL_ROLES = "SELECT * FROM ROLE";
        return jdbc.query(SELECT_ALL_ROLES, new RoleMapper());
    }

    @Override
    public void deleteRole(int id) {
        final String DELETE_USER_ROLE = "DELETE FROM USER_ROLE WHERE ROLEID = ?";      
        final String DELETE_ROLE = "DELETE FROM ROLE WHERE ROLEID = ?";
        jdbc.update(DELETE_USER_ROLE, id);
        jdbc.update(DELETE_ROLE, id);
    }

    @Override
    public void updateRole(Role role) {
        final String UPDATE_ROLE = "UPDATE ROLE SET ROLE = ? WHERE ROLEID = ?";
        jdbc.update(UPDATE_ROLE, role.getRole(), role.getId());
    }

    @Override
    @Transactional
    public Role createRole(Role role) {
        Role tempRole = getRoleByRole(role.getRole()); 
        if(tempRole==null){
        final String INSERT_ROLE = "INSERT INTO ROLE(ROLE) VALUES(?)";
        jdbc.update(INSERT_ROLE, role.getRole());
        int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        role.setId(newId);
        return role;
        }
        return tempRole; 
    }
    
    public static final class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("ROLEID"));
            role.setRole(rs.getString("ROLE"));
            return role;
        }
    }
}
