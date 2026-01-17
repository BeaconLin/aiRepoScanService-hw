package reposcan.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reposcan.demo.dao.ScanResultDao;
import reposcan.demo.entity.ScanResult;
import reposcan.demo.service.ScanResultService;
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
    private ScanResultDao scanResultDao;

    @Override
    public List<ScanResult> getResultsByTaskId(String taskId) {
        return scanResultDao.findByTaskId(taskId);
    }

    @Override
    public ScanResult getResultById(Long id) {
        return scanResultDao.findById(id);
    }

    @Override
    public boolean batchInsertResults(List<ScanResult> results) {
        if (results == null || results.isEmpty()) {
            return false;
        }
        return scanResultDao.batchInsert(results) > 0;
    }

    @Override
    public boolean markResult(Long id, Boolean isIssue, String marker) {
        Timestamp markTime = new Timestamp(System.currentTimeMillis());
        return scanResultDao.updateMark(id, isIssue, marker, markTime) > 0;
    }

    @Override
    public Map<String, Object> getTaskStatistics(String taskId) {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总缺陷数
        Long totalCount = scanResultDao.countByTaskId(taskId);
        statistics.put("totalCount", totalCount != null ? totalCount : 0);
        
        // 缺陷类型分布
        List<Map<String, Object>> ruleDistribution = scanResultDao.countByRuleName(taskId);
        statistics.put("ruleDistribution", ruleDistribution);
        
        // 标记状态统计
        Map<String, Object> markStatus = scanResultDao.countByMarkStatus(taskId);
        statistics.put("markStatus", markStatus);
        
        return statistics;
    }
}
