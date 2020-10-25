package blog.dao;

import blog.TestApplicationConfiguration;
import blog.dto.Role;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author mohammedchowdhury
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoleDaoDBTest {

    @Autowired
    RoleDao roleDao;

    @BeforeEach
    @AfterEach
    public void setUp() {
        //remove all Roll
        List<Role> listOfRolls = roleDao.getAllRoles();
        for (Role role : listOfRolls) {
            roleDao.deleteRole(role.getId());
        }
    }

    @Test
    public void testCreateRole() {
        //Arrange 
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        //Act
        role = roleDao.createRole(role);
        Role newRole = roleDao.getRoleById(role.getId());
        //Assert
        assertEquals(newRole, role);
    }

    @Test
    public void testGetRoleById() {
        //Arrange 
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        //Act
        role = roleDao.createRole(role);
        Role newRole = roleDao.getRoleById(role.getId());
        //Assert
        assertEquals(newRole, role);
    }

    @Test
    public void testGetRoleById2() {
        //Arrange 
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        //Act
        role = roleDao.createRole(role);
        Role newRole = roleDao.getRoleById(role.getId() + 1);
        //Assert
        assertNull(newRole);
    }

    @Test
    public void testgetRoleByRole() {
        //Arrange 
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        //Act
        role = roleDao.createRole(role);
        Role newRole = roleDao.getRoleByRole(role.getRole());
        //Assert
        assertEquals(newRole, role);
    }

    @Test
    public void testUpdateRole() {
        //Arrange 
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        //Act
        role = roleDao.createRole(role);
        role.setRole("ROLE_USER");
        roleDao.updateRole(role);
        Role newRole = roleDao.getRoleByRole(role.getRole());
        //Assert
        assertEquals(newRole, role);
    }

    @Test
    public void testDeleteRole() {
        //Arrange 
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        //Act
        role = roleDao.createRole(role);
        roleDao.deleteRole(role.getId());
        Role newRole = roleDao.getRoleByRole(role.getRole());
        //Assert
        assertNull(newRole);
    }

    @Test
    public void testGetAllRoles() {
        //Arrange 
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setRole("ROLE_USER");
        //Act
        role = roleDao.createRole(role);
        role2 = roleDao.createRole(role2);

        List<Role> listOfRoles = roleDao.getAllRoles();
        int size = 2;
        int actualSize = listOfRoles.size();
        //assert
        assertEquals(size, actualSize);
        assertTrue(listOfRoles.contains(role));
        assertTrue(listOfRoles.contains(role2));
    }
}
