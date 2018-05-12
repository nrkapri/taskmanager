package com.nayan.aaho;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

public class Task {
	
	@Id
	public String taskid;
	
	public String taskCreationTime;
	
	public String taskDesc;
	
	public String taskStatus;


	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskCreationTime() {
		return taskCreationTime;
	}

	public void setTaskCreationTime(String taskCreationTime) {
		this.taskCreationTime = taskCreationTime;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
	
	
}
