package reposcan.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reposcan.demo.entity.ScanResult;
import reposcan.demo.service.ScanResultService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 扫描结果控制器
 */
@RestController
@RequestMapping("/api/scan-results")
public class ScanResultController {

    @Autowired
    private ScanResultService scanResultService;

    /**
     * 根据任务ID查询扫描结果列表
     * GET /api/scan-results?taskId=xxx
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getResultsByTaskId(@RequestParam String taskId) {
        Map<String, Object> response = new HashMap<>();
        List<ScanResult> results = scanResultService.getResultsByTaskId(taskId);
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", results);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据ID查询扫描结果详情
     * GET /api/scan-results/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getResultById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        ScanResult result = scanResultService.getResultById(id);
        if (result != null) {
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", result);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "扫描结果不存在");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * 批量插入扫描结果
     * POST /api/scan-results/batch
     */
    @PostMapping("/batch")
    public ResponseEntity<Map<String, Object>> batchInsertResults(@RequestBody List<ScanResult> results) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = scanResultService.batchInsertResults(results);
            if (success) {
                response.put("code", 200);
                response.put("message", "批量插入成功");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("code", 500);
                response.put("message", "批量插入失败");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "批量插入失败: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 标注扫描结果
     * PUT /api/scan-results/{id}/mark
     */
    @PutMapping("/{id}/mark")
    public ResponseEntity<Map<String, Object>> markResult(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        Boolean isIssue = (Boolean) request.get("isIssue");
        String marker = (String) request.get("marker");
        
        if (marker == null || marker.isEmpty()) {
            response.put("code", 400);
            response.put("message", "标记人员工号不能为空");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        boolean updated = scanResultService.markResult(id, isIssue, marker);
        if (updated) {
            response.put("code", 200);
            response.put("message", "标注成功");
            response.put("data", null);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "扫描结果不存在或标注失败");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * 获取任务统计信息
     * GET /api/scan-results/statistics?taskId=xxx
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getTaskStatistics(@RequestParam String taskId) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> statistics = scanResultService.getTaskStatistics(taskId);
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", statistics);
        return ResponseEntity.ok(response);
    }
}
