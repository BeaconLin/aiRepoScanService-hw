package reposcan.demo.entity;

import java.sql.Timestamp;

/**
 * 代码仓扫描任务实体类
 */
public class RepoScanTask {
    private String taskId;
    private String taskName;
    private String repoUrl;
    private String branch;
    private String pathList;  // 扫描路径列表，多个路径用逗号分隔
    private String s3Path;     // 扫描结果保存的s3桶路径
    private String creator;    // 任务创建人
    private Timestamp createTime;
    private String taskStatus; // 任务当前所处阶段
    private String assistantVersion; // 扫描助手版本

    public RepoScanTask() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPathList() {
        return pathList;
    }

    public void setPathList(String pathList) {
        this.pathList = pathList;
    }

    public String getS3Path() {
        return s3Path;
    }

    public void setS3Path(String s3Path) {
        this.s3Path = s3Path;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getAssistantVersion() {
        return assistantVersion;
    }

    public void setAssistantVersion(String assistantVersion) {
        this.assistantVersion = assistantVersion;
    }

    @Override
    public String toString() {
        return "RepoScanTask{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", repoUrl='" + repoUrl + '\'' +
                ", branch='" + branch + '\'' +
                ", pathList='" + pathList + '\'' +
                ", s3Path='" + s3Path + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", taskStatus='" + taskStatus + '\'' +
                ", assistantVersion='" + assistantVersion + '\'' +
                '}';
    }
}
