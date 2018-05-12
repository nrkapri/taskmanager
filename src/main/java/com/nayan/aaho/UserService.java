package com.nayan.aaho;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;


	public void register(User user)
	{
		userRepository.save(user);
	}


	public List<User> allusers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}


	public User login(String email, String pwd) {
		// TODO Auto-generated method stub
		return userRepository.findOneByEmailAndPassword(email,pwd);

	}


	public User addTask(String email,String password,Task task) {
		// TODO Auto-generated method stub
		User usr = userRepository.findOneByEmailAndPassword(email,password);
		if(usr.tasks==null)
		{
			usr.tasks= new ArrayList<Task>();
		}
		usr.tasks.add(task);
		userRepository.save(usr);
		return usr;
	}


	public User updateTaskStatus(User loggedinuser, String taskDesc, String taskStatus) {

		User usr=userRepository.findOneByEmailAndPassword(loggedinuser.getEmail(),loggedinuser.getPassword());
		
				
		for (Task t : usr.getTasks())
		{
			if (t.getTaskDesc().equals(taskDesc))
			{
				t.setTaskStatus(taskStatus);
				break;
			}
		}
		
		userRepository.save(usr); 

		return usr;
	}



}
