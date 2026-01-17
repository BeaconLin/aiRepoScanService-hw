package reposcan.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reposcan.demo.dao.ScanResultDao;
import reposcan.demo.entity.ScanResult;
import reposcan.demo.mapper.ScanResultMapper;
import java.util.List;
import java.util.Map;

/**
 * 扫描结果DAO实现类
 */
@Repository
public class ScanResultDaoImpl implements ScanResultDao {

    @Autowired
    private ScanResultMapper scanResultMapper;

    @Override
    public List<ScanResult> findByTaskId(String taskId) {
        return scanResultMapper.findByTaskId(taskId);
    }

    @Override
    public ScanResult findById(Long id) {
        return scanResultMapper.findById(id);
    }

    @Override
    public int insert(ScanResult result) {
        return scanResultMapper.insert(result);
    }

    @Override
    public int batchInsert(List<ScanResult> results) {
        return scanResultMapper.batchInsert(results);
    }

    @Override
    public int updateMark(Long id, Boolean isIssue, String marker, java.sql.Timestamp markTime) {
        return scanResultMapper.updateMark(id, isIssue, marker, markTime);
    }

    @Override
    public Long countByTaskId(String taskId) {
        return scanResultMapper.countByTaskId(taskId);
    }

    @Override
    public List<Map<String, Object>> countByRuleName(String taskId) {
        return scanResultMapper.countByRuleName(taskId);
    }

    @Override
    public Map<String, Object> countByMarkStatus(String taskId) {
        return scanResultMapper.countByMarkStatus(taskId);
    }
}
