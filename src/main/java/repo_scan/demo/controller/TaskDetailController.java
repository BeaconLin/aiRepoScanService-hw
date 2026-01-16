package repo_scan.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo_scan.demo.entity.RepoScanTask;
import repo_scan.demo.entity.ScanResult;
import repo_scan.demo.service.RepoScanTaskService;
import repo_scan.demo.service.ScanResultService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务详情控制器（整合任务信息和扫描结果）
 */
@RestController
@RequestMapping("/api/task-details")
public class TaskDetailController {

    @Autowired
    private RepoScanTaskService taskService;

    @Autowired
    private ScanResultService scanResultService;

    /**
     * 获取任务详细信息（包含任务信息和统计看板）
     * GET /api/task-details/{taskId}
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Map<String, Object>> getTaskDetail(@PathVariable String taskId) {
        Map<String, Object> response = new HashMap<>();
        
        // 查询任务信息
        RepoScanTask task = taskService.getTaskById(taskId);
        if (task == null) {
            response.put("code", 404);
            response.put("message", "任务不存在");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        // 查询统计信息
        Map<String, Object> statistics = scanResultService.getTaskStatistics(taskId);
        
        // 组装返回数据
        Map<String, Object> taskDetail = new HashMap<>();
        taskDetail.put("taskInfo", task);
        taskDetail.put("statistics", statistics);
        
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", taskDetail);
        return ResponseEntity.ok(response);
    }

    /**
     * 获取任务扫描结果列表
     * GET /api/task-details/{taskId}/results
     */
    @GetMapping("/{taskId}/results")
    public ResponseEntity<Map<String, Object>> getTaskResults(@PathVariable String taskId) {
        Map<String, Object> response = new HashMap<>();
        
        // 验证任务是否存在
        RepoScanTask task = taskService.getTaskById(taskId);
        if (task == null) {
            response.put("code", 404);
            response.put("message", "任务不存在");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        // 查询扫描结果
        List<ScanResult> results = scanResultService.getResultsByTaskId(taskId);
        
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", results);
        return ResponseEntity.ok(response);
    }
}
