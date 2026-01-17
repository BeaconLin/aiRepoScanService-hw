package reposcan.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reposcan.demo.dao.RepoScanTaskDao;
import reposcan.demo.entity.RepoScanTask;
import reposcan.demo.service.RepoScanTaskService;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * 代码仓扫描任务服务层实现类
 */
@Service
public class RepoScanTaskServiceImpl implements RepoScanTaskService {

    @Autowired
    private RepoScanTaskDao repoScanTaskDao;

    @Override
    public RepoScanTask createTask(RepoScanTask task) {
        // 生成任务ID
        if (task.getTaskId() == null || task.getTaskId().isEmpty()) {
            task.setTaskId(UUID.randomUUID().toString());
        }
        // 设置创建时间
        if (task.getCreateTime() == null) {
            task.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        // 设置默认状态
        if (task.getTaskStatus() == null || task.getTaskStatus().isEmpty()) {
            task.setTaskStatus("CREATED"); // CREATED, SCANNING, COMPLETED, FAILED
        }
        
        int result = repoScanTaskDao.insert(task);
        if (result > 0) {
            return task;
        }
        return null;
    }

    @Override
    public RepoScanTask getTaskById(String taskId) {
        return repoScanTaskDao.findByTaskId(taskId);
    }

    @Override
    public List<RepoScanTask> getAllTasks() {
        return repoScanTaskDao.findAll();
    }

    @Override
    public List<RepoScanTask> getTasksByCreator(String creator) {
        return repoScanTaskDao.findByCreator(creator);
    }

    @Override
    public boolean updateTaskStatus(String taskId, String taskStatus) {
        return repoScanTaskDao.updateTaskStatus(taskId, taskStatus) > 0;
    }
}
