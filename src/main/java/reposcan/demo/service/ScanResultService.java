package reposcan.demo.service;

import reposcan.demo.entity.ScanResult;
import java.util.List;
import java.util.Map;

/**
 * 扫描结果服务层接口
 */
public interface ScanResultService {
    /**
     * 根据任务ID查询扫描结果列表
     */
    List<ScanResult> getResultsByTaskId(String taskId);

    /**
     * 根据ID查询扫描结果
     */
    ScanResult getResultById(Long id);

    /**
     * 批量插入扫描结果
     */
    boolean batchInsertResults(List<ScanResult> results);

    /**
     * 标注扫描结果
     */
    boolean markResult(Long id, Boolean isIssue, String marker);

    /**
     * 获取任务统计信息
     */
    Map<String, Object> getTaskStatistics(String taskId);
}
