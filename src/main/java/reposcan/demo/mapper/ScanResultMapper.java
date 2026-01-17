package reposcan.demo.mapper;

import org.apache.ibatis.annotations.*;
import reposcan.demo.entity.ScanResult;
import java.util.List;
import java.util.Map;

/**
 * 扫描结果Mapper接口
 */
@Mapper
public interface ScanResultMapper {
    /**
     * 根据任务ID查询扫描结果列表
     */
    @Select("SELECT * FROM t_scan_result WHERE task_id = #{taskId} ORDER BY line ASC")
    List<ScanResult> findByTaskId(String taskId);

    /**
     * 根据ID查询扫描结果
     */
    @Select("SELECT * FROM t_scan_result WHERE id = #{id}")
    ScanResult findById(Long id);

    /**
     * 插入扫描结果
     */
    @Insert("INSERT INTO t_scan_result(task_id, file_name, rule_name, line, code_block, context, warn, is_issue, marker, mark_time) " +
            "VALUES(#{taskId}, #{fileName}, #{ruleName}, #{line}, #{codeBlock}, #{context}, #{warn}, #{isIssue}, #{marker}, #{markTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ScanResult result);

    /**
     * 批量插入扫描结果
     */
    @Insert("<script>" +
            "INSERT INTO t_scan_result(task_id, file_name, rule_name, line, code_block, context, warn) VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.taskId}, #{item.fileName}, #{item.ruleName}, #{item.line}, #{item.codeBlock}, #{item.context}, #{item.warn})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("list") List<ScanResult> results);

    /**
     * 更新扫描结果标注
     */
    @Update("UPDATE t_scan_result SET is_issue = #{isIssue}, marker = #{marker}, mark_time = #{markTime} WHERE id = #{id}")
    int updateMark(@Param("id") Long id, @Param("isIssue") Boolean isIssue, 
                   @Param("marker") String marker, @Param("markTime") java.sql.Timestamp markTime);

    /**
     * 统计任务扫描结果数量
     */
    @Select("SELECT COUNT(*) FROM t_scan_result WHERE task_id = #{taskId}")
    Long countByTaskId(String taskId);

    /**
     * 统计任务扫描结果按缺陷类型分布
     */
    @Select("SELECT rule_name, COUNT(*) as count FROM t_scan_result WHERE task_id = #{taskId} GROUP BY rule_name")
    List<Map<String, Object>> countByRuleName(String taskId);

    /**
     * 统计任务扫描结果按标记状态分布
     */
    @Select("SELECT " +
            "SUM(CASE WHEN is_issue IS NULL THEN 1 ELSE 0 END) as unmarked, " +
            "SUM(CASE WHEN is_issue = 1 THEN 1 ELSE 0 END) as is_issue, " +
            "SUM(CASE WHEN is_issue = 0 THEN 1 ELSE 0 END) as not_issue " +
            "FROM t_scan_result WHERE task_id = #{taskId}")
    Map<String, Object> countByMarkStatus(String taskId);
}
