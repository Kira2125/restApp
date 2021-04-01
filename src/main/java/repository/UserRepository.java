package repository;

import model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    User update(User user, int id);

    void delete(int id);

    User get(int id);

    List<User> getAll();
}
