package repo_scan.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo_scan.demo.dao.UserDao;
import repo_scan.demo.entity.User;
import repo_scan.demo.service.UserService;
import java.util.List;

/**
 * 用户服务层实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> getUsersByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User createUser(User user) {
        int result = userDao.insert(user);
        if (result > 0) {
            return user;
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        int result = userDao.update(user);
        if (result > 0) {
            return userDao.findById(user.getId());
        }
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        return userDao.deleteById(id) > 0;
    }
}
