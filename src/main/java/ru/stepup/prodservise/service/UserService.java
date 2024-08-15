package ru.stepup.prodservise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.prodservise.entity.User;
import ru.stepup.prodservise.dao.UserDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service("UserService")
public class UserService {

    private UserDao userDao;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Long save (User user) {
        return userDao.save(user);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public Optional<User> getUser(long id) throws SQLException {
        return Optional.ofNullable(userDao.getUser(id));
    }

    public void updateUser(long id, String name) {
        userDao.updateUser(id, name);
    }

    public void deleteAll() {
        userDao.deleteAll();
    }
}


