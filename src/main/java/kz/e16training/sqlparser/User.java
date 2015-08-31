package kz.e16training.sqlparser;

import java.util.ArrayList;
import java.util.List;

/**
 * model of User
 */
public class User {
    private String name;
    private String email;
    private String hash;
    private List<User> users;

    public User() {}

    public User(String name, String email, String hash) {
        this.name = name;
        this.email = email;
        this.hash = hash;
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

}
