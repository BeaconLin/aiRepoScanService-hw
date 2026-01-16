package repo_scan.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo_scan.demo.entity.RepoScanTask;
import repo_scan.demo.mapper.RepoScanTaskMapper;
import repo_scan.demo.service.RepoScanTaskService;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * 代码仓扫描任务服务层实现类
 */
@Service
public class RepoScanTaskServiceImpl implements RepoScanTaskService {

    @Autowired
    private RepoScanTaskMapper taskMapper;

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
        
        int result = taskMapper.insert(task);
        if (result > 0) {
            return task;
        }
        return null;
    }

    @Override
    public RepoScanTask getTaskById(String taskId) {
        return taskMapper.findByTaskId(taskId);
    }

    @Override
    public List<RepoScanTask> getAllTasks() {
        return taskMapper.findAll();
    }

    @Override
    public List<RepoScanTask> getTasksByCreator(String creator) {
        return taskMapper.findByCreator(creator);
    }

    @Override
    public boolean updateTaskStatus(String taskId, String taskStatus) {
        return taskMapper.updateTaskStatus(taskId, taskStatus) > 0;
    }
}
