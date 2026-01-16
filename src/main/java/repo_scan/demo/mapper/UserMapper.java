package repo_scan.demo.mapper;

import org.apache.ibatis.annotations.*;
import repo_scan.demo.entity.User;
import java.util.List;

/**
 * MyBatis Mapper接口
 */
@Mapper
public interface UserMapper {
    /**
     * 根据ID查询用户
     */
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    /**
     * 查询所有用户
     */
    @Select("SELECT * FROM users")
    List<User> findAll();

    /**
     * 根据姓名查询用户
     */
    @Select("SELECT * FROM users WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<User> findByName(String name);

    /**
     * 插入用户
     */
    @Insert("INSERT INTO users(name, email, age) VALUES(#{name}, #{email}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 更新用户
     */
    @Update("UPDATE users SET name = #{name}, email = #{email}, age = #{age} WHERE id = #{id}")
    int update(User user);

    /**
     * 删除用户
     */
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Long id);
}
