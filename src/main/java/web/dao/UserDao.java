package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();

    public User show(Long id);

    public void save(User user);

    public void update(Long id, User updatedUser);

    public void delete(Long id);
}