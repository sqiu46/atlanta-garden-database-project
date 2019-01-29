package main.java.db;

import main.java.model.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUsernameAndPassword(String username, String password);

    List<User> findByType(User.UserType userType);
    List<User> findAll();
    List<User> findByUsernameOrEmail(String username, String email);
    List<User> findAllUsersOfTypeOrdered(User.UserType userType, String orderByColumn, String searchTerm,
                                                String termLike, boolean isAscending);

    boolean insertUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(User user);
}
