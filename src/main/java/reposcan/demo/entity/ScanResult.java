package reposcan.demo.entity;

import java.sql.Timestamp;

/**
 * 扫描结果实体类
 */
public class ScanResult {
    private Long id;
    private String taskId;        // 关联的任务ID
    private String fileName;     // 文件名称
    private String ruleName;     // 扫描出的问题缺陷名称
    private Integer line;        // 问题在当前文件第几行
    private String codeBlock;    // 问题切片的代码块
    private String context;      // 上下文代码
    private String warn;         // 模型给出的详细问题说明
    private Boolean isIssue;     // 人工判定是否是问题（true-是问题，false-不是问题，null-未标注）
    private String marker;       // 标记人员工号
    private Timestamp markTime;  // 标注时间

    public ScanResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getCodeBlock() {
        return codeBlock;
    }

    public void setCodeBlock(String codeBlock) {
        this.codeBlock = codeBlock;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public Boolean getIsIssue() {
        return isIssue;
    }

    public void setIsIssue(Boolean isIssue) {
        this.isIssue = isIssue;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public Timestamp getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Timestamp markTime) {
        this.markTime = markTime;
    }

    @Override
    public String toString() {
        return "ScanResult{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", line=" + line +
                ", codeBlock='" + codeBlock + '\'' +
                ", context='" + context + '\'' +
                ", warn='" + warn + '\'' +
                ", isIssue=" + isIssue +
                ", marker='" + marker + '\'' +
                ", markTime=" + markTime +
                '}';
    }
}
