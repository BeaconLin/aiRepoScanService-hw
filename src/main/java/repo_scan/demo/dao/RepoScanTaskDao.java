package reposcan.demo.dao;

import reposcan.demo.entity.RepoScanTask;
import java.util.List;

/**
 * 代码仓扫描任务DAO接口
 */
public interface RepoScanTaskDao {
    /**
     * 根据任务ID查询任务
     */
    RepoScanTask findByTaskId(String taskId);

    /**
     * 查询所有任务
     */
    List<RepoScanTask> findAll();

    /**
     * 根据创建人查询任务列表
     */
    List<RepoScanTask> findByCreator(String creator);

    /**
     * 插入任务
     */
    int insert(RepoScanTask task);

    /**
     * 更新任务状态
     */
    int updateTaskStatus(String taskId, String taskStatus);

    /**
     * 更新任务信息
     */
    int update(RepoScanTask task);
}
