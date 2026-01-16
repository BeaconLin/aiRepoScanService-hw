package repo_scan.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo_scan.demo.entity.RepoScanTask;
import repo_scan.demo.service.RepoScanTaskService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码仓扫描任务控制器
 */
@RestController
@RequestMapping("/api/tasks")
public class RepoScanTaskController {

    @Autowired
    private RepoScanTaskService taskService;

    /**
     * 创建代码仓扫描任务
     * POST /api/tasks
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody RepoScanTask task) {
        Map<String, Object> response = new HashMap<>();
        try {
            RepoScanTask createdTask = taskService.createTask(task);
            if (createdTask != null) {
                response.put("code", 200);
                response.put("message", "创建成功");
                response.put("data", createdTask);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("code", 500);
                response.put("message", "创建失败");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "创建失败: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 查询所有任务
     * GET /api/tasks
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTasks() {
        Map<String, Object> response = new HashMap<>();
        List<RepoScanTask> tasks = taskService.getAllTasks();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", tasks);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据创建人查询任务列表
     * GET /api/tasks?creator=xxx
     */
    @GetMapping(params = "creator")
    public ResponseEntity<Map<String, Object>> getTasksByCreator(@RequestParam String creator) {
        Map<String, Object> response = new HashMap<>();
        List<RepoScanTask> tasks = taskService.getTasksByCreator(creator);
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", tasks);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据任务ID查询任务详情
     * GET /api/tasks/{taskId}
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Map<String, Object>> getTaskById(@PathVariable String taskId) {
        Map<String, Object> response = new HashMap<>();
        RepoScanTask task = taskService.getTaskById(taskId);
        if (task != null) {
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", task);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "任务不存在");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * 更新任务状态
     * PUT /api/tasks/{taskId}/status
     */
    @PutMapping("/{taskId}/status")
    public ResponseEntity<Map<String, Object>> updateTaskStatus(
            @PathVariable String taskId,
            @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String taskStatus = request.get("taskStatus");
        if (taskStatus == null || taskStatus.isEmpty()) {
            response.put("code", 400);
            response.put("message", "任务状态不能为空");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        boolean updated = taskService.updateTaskStatus(taskId, taskStatus);
        if (updated) {
            response.put("code", 200);
            response.put("message", "更新成功");
            response.put("data", null);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "任务不存在或更新失败");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
