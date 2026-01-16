package repo_scan.demo.service;

import repo_scan.demo.entity.RepoScanTask;
import java.util.List;

/**
 * 代码仓扫描任务服务层接口
 */
public interface RepoScanTaskService {
    /**
     * 创建扫描任务
     */
    RepoScanTask createTask(RepoScanTask task);

    /**
     * 根据任务ID查询任务详情
     */
    RepoScanTask getTaskById(String taskId);

    /**
     * 查询所有任务
     */
    List<RepoScanTask> getAllTasks();

    /**
     * 根据创建人查询任务列表
     */
    List<RepoScanTask> getTasksByCreator(String creator);

    /**
     * 更新任务状态
     */
    boolean updateTaskStatus(String taskId, String taskStatus);
}
