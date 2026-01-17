package reposcan.demo.service;

import reposcan.demo.entity.User;
import java.util.List;

/**
 * 用户服务层接口
 */
public interface UserService {
    /**
     * 根据ID查询用户
     */
    User getUserById(Long id);

    /**
     * 查询所有用户
     */
    List<User> getAllUsers();

    /**
     * 根据姓名查询用户
     */
    List<User> getUsersByName(String name);

    /**
     * 创建用户
     */
    User createUser(User user);

    /**
     * 更新用户
     */
    User updateUser(User user);

    /**
     * 删除用户
     */
    boolean deleteUser(Long id);
}
