package reposcan.demo.dao;

import reposcan.demo.entity.ScanResult;
import java.util.List;
import java.util.Map;

/**
 * 扫描结果DAO接口
 */
public interface ScanResultDao {
    /**
     * 根据任务ID查询扫描结果列表
     */
    List<ScanResult> findByTaskId(String taskId);

    /**
     * 根据ID查询扫描结果
     */
    ScanResult findById(Long id);

    /**
     * 插入扫描结果
     */
    int insert(ScanResult result);

    /**
     * 批量插入扫描结果
     */
    int batchInsert(List<ScanResult> results);

    /**
     * 更新扫描结果标注
     */
    int updateMark(Long id, Boolean isIssue, String marker, java.sql.Timestamp markTime);

    /**
     * 统计任务扫描结果数量
     */
    Long countByTaskId(String taskId);

    /**
     * 统计任务扫描结果按缺陷类型分布
     */
    List<Map<String, Object>> countByRuleName(String taskId);

    /**
     * 统计任务扫描结果按标记状态分布
     */
    Map<String, Object> countByMarkStatus(String taskId);
}
