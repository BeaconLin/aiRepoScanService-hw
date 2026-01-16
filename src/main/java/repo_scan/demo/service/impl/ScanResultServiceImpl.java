package repo_scan.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo_scan.demo.entity.ScanResult;
import repo_scan.demo.mapper.ScanResultMapper;
import repo_scan.demo.service.ScanResultService;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 扫描结果服务层实现类
 */
@Service
public class ScanResultServiceImpl implements ScanResultService {

    @Autowired
    private ScanResultMapper scanResultMapper;

    @Override
    public List<ScanResult> getResultsByTaskId(String taskId) {
        return scanResultMapper.findByTaskId(taskId);
    }

    @Override
    public ScanResult getResultById(Long id) {
        return scanResultMapper.findById(id);
    }

    @Override
    public boolean batchInsertResults(List<ScanResult> results) {
        if (results == null || results.isEmpty()) {
            return false;
        }
        return scanResultMapper.batchInsert(results) > 0;
    }

    @Override
    public boolean markResult(Long id, Boolean isIssue, String marker) {
        Timestamp markTime = new Timestamp(System.currentTimeMillis());
        return scanResultMapper.updateMark(id, isIssue, marker, markTime) > 0;
    }

    @Override
    public Map<String, Object> getTaskStatistics(String taskId) {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总缺陷数
        Long totalCount = scanResultMapper.countByTaskId(taskId);
        statistics.put("totalCount", totalCount != null ? totalCount : 0);
        
        // 缺陷类型分布
        List<Map<String, Object>> ruleDistribution = scanResultMapper.countByRuleName(taskId);
        statistics.put("ruleDistribution", ruleDistribution);
        
        // 标记状态统计
        Map<String, Object> markStatus = scanResultMapper.countByMarkStatus(taskId);
        statistics.put("markStatus", markStatus);
        
        return statistics;
    }
}
