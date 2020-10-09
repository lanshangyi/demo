package com.example.demo.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class BizQuartz {
    private Integer id;
    
    @NotNull(message = "定时任务名称不能为空")
    private String quartzName;
    
    private Boolean status;
    
    @NotNull(message = "bean名称不能为空")
    private String beanName;
    
    @NotNull(message = "方法任务名称不能为空")
    private String methodName;

    private String params;
    
    @NotNull(message = "定时表达式不能为空")
    private String cronExpression;

    private String cronExpressionName;

    private Integer isPause;
    
    private Date startTime;
    
    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuartzName() {
        return quartzName;
    }

    public void setQuartzName(String quartzName) {
        this.quartzName = quartzName == null ? null : quartzName.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName == null ? null : beanName.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim();
    }

    public String getCronExpressionName() {
        return cronExpressionName;
    }

    public void setCronExpressionName(String cronExpressionName) {
        this.cronExpressionName = cronExpressionName == null ? null : cronExpressionName.trim();
    }

    public Integer getIsPause() {
        return isPause;
    }

    public void setIsPause(Integer isPause) {
        this.isPause = isPause;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}