package web.service;

import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.TypedQuery;
import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User show(Long id);

    public void save(User user);

    public void update(Long id, User updatedUser);

    public void delete(Long id);
}