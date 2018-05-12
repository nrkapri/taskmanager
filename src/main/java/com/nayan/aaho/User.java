package com.nayan.aaho;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class User {

	@Id
	public String eid;

	public String firstName;
	

	public String lastName;

	public String email;
	


	public String password;
	
	
	public ArrayList<Task> tasks;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public User() {}

	public User(String eid,String firstName, String lastName,String email, String password  ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email=email;
		this.eid=eid;
		this.password=password;
		
		this.tasks = new ArrayList<Task>();
	}

	@Override
	public String toString() {
		return String.format(
				"User [eid=%s, firstName='%s', lastName='%s', email='%s',  password ='%s']",
				eid, firstName, lastName,email,password);
	}

	public int getPendingTaskCount() {
		// TODO Auto-generated method stub
		int count=0;
		for (Task t : tasks)
		{
			if(!t.getTaskStatus().equals("C") &&  !t.getTaskStatus().equals("D"))
			{
				count++;
			}
		}
		return count;
	}

	public int  getDoneTaskCount() {
		// TODO Auto-generated method stub
		int count=0;
		for (Task t : tasks)
		{
			if(t.getTaskStatus().equals("C") )
			{
				count++;
			}
		}
		return count;
	}

	public int  getDeletdTaskCount() {
		int count=0;
		for (Task t : tasks)
		{
			if(t.getTaskStatus().equals("D") )
			{
				count++;
			}
		}
		return count;
	}


	
}
