package com.yl.distribute.scheduler.client.callback;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yl.distribute.scheduler.client.TaskClient;
import com.yl.distribute.scheduler.client.job.ObjectId;
import com.yl.distribute.scheduler.common.bean.TaskResponse;
import com.yl.distribute.scheduler.common.bean.TaskRequest;
import com.yl.distribute.scheduler.common.enums.TaskStatus;

public class TaskCallback{
	
    private static Log LOG = LogFactory.getLog(TaskCallback.class);
    
    private TaskRequest task;
    
    public TaskCallback(TaskRequest task) {
        this.task = task;
    }
    
    public void onRead(TaskResponse response) throws Exception {
    	System.out.println(task.getTaskId() + "-" + task.getId() + "返回状态是" + response.getTaskId() + response.getTaskStatus());
    	LOG.info(task.getTaskId() + "-" + task.getId() + "返回状态是" + response.getTaskId() +  response.getTaskStatus());
    	
    	TaskResponse taskResponse = TaskResponseManager.get(task.getJob().getJobId());
        if(taskResponse == null){
            TaskResponseManager.add(task.getJob().getJobId(),response);
        }else{
            taskResponse.setFailedTimes(response.getFailedTimes());            
            taskResponse.setTaskStatus(response.getTaskStatus());
        }
        
        resubmitIfNeccesery(response);
    }
    
    private void resubmitIfNeccesery(TaskResponse responseTask) throws Exception {
        if(responseTask.getTaskStatus() == TaskStatus.FAILED
                && task.getFailedTimes() < task.getJob().getRetryTimes()) {   
            TaskRequest newTask = new TaskRequest();
            initNewTask(newTask);            
            TaskClient.getInstance().submit(newTask);            
        }
    }
    
    private void initNewTask(TaskRequest newTask) {
        newTask.setId(new ObjectId().toHexString());
        newTask.setTaskId(task.getTaskId());
        newTask.setJob(task.getJob());
        newTask.setStartTime(new Date());
        newTask.setEndTime(null);
        newTask.setLastFailedServer(task.getRunningServer());
        newTask.setRunningServer("");
        newTask.setFailedTimes(task.getFailedTimes() + 1);
        newTask.setStdOutputUrl("");
        newTask.setErrorOutputUrl("");
        newTask.setTaskStatus(TaskStatus.SUBMIT);
    }
}