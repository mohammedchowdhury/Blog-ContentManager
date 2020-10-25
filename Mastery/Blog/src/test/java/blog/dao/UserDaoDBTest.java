package blog.dao;

import blog.TestApplicationConfiguration;
import blog.dto.Role;
import blog.dto.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class UserDaoDBTest {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @BeforeEach
    @AfterEach
    public void setUp() {
        //remove all User
        List<User> listOfUsers = userDao.getAllUsers();
        for (User user : listOfUsers) {
            userDao.deleteUser(user.getId());
        }
        //remove all Roll
        List<Role> listOfRolls = roleDao.getAllRoles();
        for (Role role : listOfRolls) {
            roleDao.deleteRole(role.getId());
        }
    }

    @Test
    public void testCreateUser() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        //act
        user = userDao.createUser(user);
        User newUser = userDao.getUserById(user.getId());
        //assert
        assertEquals(user, newUser);
    }

    @Test
    public void testGetUserById() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        //act
        user = userDao.createUser(user);
        User newUser = userDao.getUserById(user.getId());
        //assert
        assertEquals(user, newUser);
    }

    @Test
    public void testGetUserById2() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        //act
        user = userDao.createUser(user);
        User newUser = userDao.getUserById(user.getId() + 1);
        //assert
        assertNull(newUser);
    }

    @Test
    public void testGetUserByUsername() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        //act
        user = userDao.createUser(user);
        User newUser = userDao.getUserByUsername(user.getUsername());
        //assert
        assertEquals(user, newUser);
    }

    @Test
    public void testGetUserByUsername2() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        //act
        user = userDao.createUser(user);
        User newUser = userDao.getUserByUsername(user.getUsername() + "a");
        //assert
        assertNull(newUser);
    }

    @Test
    public void testUpdateUser() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        //act
        user = userDao.createUser(user);
        user.setPassword("123");
        user.setUsername("ROLE_USER");
        userDao.updateUser(user);
        User newUser = userDao.getUserById(user.getId());
        //assert
        assertEquals(user, newUser);
    }

    @Test
    public void testDeleteUser() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        //act
        user = userDao.createUser(user);
        userDao.deleteUser(user.getId());
        User newUser = userDao.getUserById(user.getId());
        //assert
        assertNull(newUser);
    }

    @Test
    public void testGetAllUsers() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);

        User user2 = new User();
        user2.setPassword("password");
        user2.setUsername("user");
        user2.setRoles(roles);
        //act
        user = userDao.createUser(user);
        user2 = userDao.createUser(user2);
        List<User> listOfUsers = userDao.getAllUsers();
        int size = 2;
        int actualSize = listOfUsers.size();
        //assert
        assertEquals(size, actualSize);
        assertTrue(listOfUsers.contains(user));
        assertTrue(listOfUsers.contains(user2));
    }
}