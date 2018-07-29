package com.yl.distribute.scheduler.common.bean;

import java.util.List;

public class JobReleation {
    
    private String jobId;
    private List<JobConf> parentJobs;
    private List<JobConf> childJobs;
    
    public String getJobId() {
        return jobId;
    }
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    public List<JobConf> getParentJobs() {
        return parentJobs;
    }
    public void setParentJobs(List<JobConf> parentJobs) {
        this.parentJobs = parentJobs;
    }
    public List<JobConf> getChildJobs() {
        return childJobs;
    }
    public void setChildJobs(List<JobConf> childJobs) {
        this.childJobs = childJobs;
    }    
}
