package blog.dao;

import blog.dto.User;
import java.util.List;

public interface UserDao {
    User getUserById(int id);
    User getUserByUsername(String username);
    User getActiveUserByUsername(String username); 
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);
    User createUser(User user);
}
