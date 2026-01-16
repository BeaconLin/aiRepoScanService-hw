package repo_scan.demo.dao;

import repo_scan.demo.entity.User;
import java.util.List;

/**
 * 用户数据访问层接口
 */
public interface UserDao {
    /**
     * 根据ID查询用户
     */
    User findById(Long id);

    /**
     * 查询所有用户
     */
    List<User> findAll();

    /**
     * 根据姓名查询用户
     */
    List<User> findByName(String name);

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 更新用户
     */
    int update(User user);

    /**
     * 删除用户
     */
    int deleteById(Long id);
}
