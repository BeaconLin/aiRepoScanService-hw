package reposcan.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reposcan.demo.dao.RepoScanTaskDao;
import reposcan.demo.entity.RepoScanTask;
import reposcan.demo.mapper.RepoScanTaskMapper;
import java.util.List;

/**
 * 代码仓扫描任务DAO实现类
 */
@Repository
public class RepoScanTaskDaoImpl implements RepoScanTaskDao {

    @Autowired
    private RepoScanTaskMapper repoScanTaskMapper;

    @Override
    public RepoScanTask findByTaskId(String taskId) {
        return repoScanTaskMapper.findByTaskId(taskId);
    }

    @Override
    public List<RepoScanTask> findAll() {
        return repoScanTaskMapper.findAll();
    }

    @Override
    public List<RepoScanTask> findByCreator(String creator) {
        return repoScanTaskMapper.findByCreator(creator);
    }

    @Override
    public int insert(RepoScanTask task) {
        return repoScanTaskMapper.insert(task);
    }

    @Override
    public int updateTaskStatus(String taskId, String taskStatus) {
        return repoScanTaskMapper.updateTaskStatus(taskId, taskStatus);
    }

    @Override
    public int update(RepoScanTask task) {
        return repoScanTaskMapper.update(task);
    }
}
