package repo_scan.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo_scan.demo.entity.User;
import repo_scan.demo.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据ID查询用户
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.getUserById(id);
        if (user != null) {
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", user);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "用户不存在");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * 查询所有用户
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        List<User> users = userService.getAllUsers();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", users);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据姓名查询用户
     * GET /api/users/search?name=xxx
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getUsersByName(@RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        List<User> users = userService.getUsersByName(name);
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", users);
        return ResponseEntity.ok(response);
    }

    /**
     * 创建用户
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        User createdUser = userService.createUser(user);
        if (createdUser != null) {
            response.put("code", 200);
            response.put("message", "创建成功");
            response.put("data", createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("code", 500);
            response.put("message", "创建失败");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 更新用户
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            response.put("code", 200);
            response.put("message", "更新成功");
            response.put("data", updatedUser);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "用户不存在或更新失败");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * 删除用户
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            response.put("code", 200);
            response.put("message", "删除成功");
            response.put("data", null);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "用户不存在或删除失败");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
