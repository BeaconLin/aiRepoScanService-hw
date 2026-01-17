package reposcan.demo.mapper;

import org.apache.ibatis.annotations.*;
import reposcan.demo.entity.RepoScanTask;
import java.util.List;

/**
 * 代码仓扫描任务Mapper接口
 */
@Mapper
public interface RepoScanTaskMapper {
    /**
     * 根据任务ID查询任务
     */
    @Select("SELECT * FROM t_repo_scan_task WHERE task_id = #{taskId}")
    RepoScanTask findByTaskId(String taskId);

    /**
     * 查询所有任务
     */
    @Select("SELECT * FROM t_repo_scan_task ORDER BY create_time DESC")
    List<RepoScanTask> findAll();

    /**
     * 根据创建人查询任务列表
     */
    @Select("SELECT * FROM t_repo_scan_task WHERE creator = #{creator} ORDER BY create_time DESC")
    List<RepoScanTask> findByCreator(String creator);

    /**
     * 插入任务
     */
    @Insert("INSERT INTO t_repo_scan_task(task_id, task_name, repo_url, branch, path_list, s3_path, creator, create_time, task_status, assistant_version) " +
            "VALUES(#{taskId}, #{taskName}, #{repoUrl}, #{branch}, #{pathList}, #{s3Path}, #{creator}, #{createTime}, #{taskStatus}, #{assistantVersion})")
    int insert(RepoScanTask task);

    /**
     * 更新任务状态
     */
    @Update("UPDATE t_repo_scan_task SET task_status = #{taskStatus} WHERE task_id = #{taskId}")
    int updateTaskStatus(@Param("taskId") String taskId, @Param("taskStatus") String taskStatus);

    /**
     * 更新任务信息
     */
    @Update("UPDATE t_repo_scan_task SET task_name = #{taskName}, repo_url = #{repoUrl}, branch = #{branch}, " +
            "path_list = #{pathList}, s3_path = #{s3Path}, task_status = #{taskStatus}, assistant_version = #{assistantVersion} " +
            "WHERE task_id = #{taskId}")
    int update(RepoScanTask task);
}
