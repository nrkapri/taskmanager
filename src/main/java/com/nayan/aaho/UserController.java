package com.nayan.aaho;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;


@RequestMapping("/")
@Controller
@SessionAttributes({"loggedinuser"})
public class UserController {

	@Autowired 
	UserService userservice;

	private String validateLoggedinIUser()
	{
		return null;
		
	}
	
	@GetMapping("/register")
	public String register(Model m) {
		//  model.addAttribute("name", firstname);
		return "register";
	}


	@PostMapping("/register")
	public RedirectView register(@RequestParam(name="firstname", required=true) String firstname,
			@RequestParam(name="lastname", required=true) String lastname,
			@RequestParam(name="eid", required=true) String eid,
			@RequestParam(name="email", required=true) String email,
			@RequestParam(name="password", required=true) String password,
			Model m) {

		User usr = new User(eid,firstname,lastname,email, password  );

		userservice.register(usr);
		return new RedirectView("/login");
		//  model.addAttribute("name", firstname);
		//return "login";
	}

	@GetMapping("/login")
	public String login() {
		
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam(name="email", required=true) String email ,
			@RequestParam(name="pwd", required=true) String pwd,
			Model model) {
		User usr =userservice.login(email,pwd);
		if(usr!=null)
		{
			model.addAttribute("message","Welcome "+usr.getFirstName() + " "+ usr.getLastName());
			model.addAttribute("user",usr);
			model.addAttribute("task",new Task());		
			model.addAttribute("loggedinuser", usr);
			return "redirect:/tasks";
		}
		else 
		{
			model.addAttribute("message", "Login failed");
			return "login";
		}
		
	}

	@PostMapping("/addTask")
	public String addTask(@RequestParam(name="email", required=true) String email ,
			@RequestParam(name="password", required=true) String pwd,
			@Valid @ModelAttribute("task") Task task,
			Model model)
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
		task.setTaskCreationTime(strDate);
		task.setTaskStatus("P");
		User usr=userservice.addTask(email,pwd,task);
		model.addAttribute("user",usr);
		model.addAttribute("task",new Task());
		model.addAttribute("message","Welcome "+usr.getFirstName() + " "+ usr.getLastName());
		return "redirect:/tasks";
	}
	
	@GetMapping("/tasks") 
	public String mytasks(@ModelAttribute("loggedinuser")User loggedinuser,Model model )
	{
		if(loggedinuser==null)
		{
			return "redirect:/login";
		}
		User usr=userservice.login(loggedinuser.getEmail(),loggedinuser.getPassword());
		usr.getTasks().sort(new Comparator<Task>() {

			@Override
			public int compare(Task o1, Task o2) {
				if(o1.getTaskStatus().isEmpty()||o1.getTaskStatus().equals("P")) return -1;
				else if (o2.getTaskStatus().isEmpty()||o2.getTaskStatus().equals("P")) return 1;
				else return o1.getTaskStatus().compareTo(o2.getTaskStatus());
			}
			
		});
		model.addAttribute("pendingTasks",usr.getPendingTaskCount());
		model.addAttribute("doneTasks",usr.getDoneTaskCount());
		model.addAttribute("deletedTasks",usr.getDeletdTaskCount());
		
		model.addAttribute("user",usr);
		model.addAttribute("task",new Task());
		model.addAttribute("message","Welcome "+usr.getFirstName() + " "+ usr.getLastName());
		
		
		
		
		return "mytasks";
	}
	
	
	@GetMapping("/allusers")
	public @ResponseBody Iterable<User> allusers(  ) {
		return  userservice.allusers();
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,SessionStatus sessionStatus)
	{
		sessionStatus.setComplete();
		HttpSession session = request.getSession();  
		//session.removeAttribute("loggedinuser");
	     session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping("/updateTaskStatus")
	public String updateTaskStatus(@ModelAttribute("loggedinuser")User loggedinuser,
			@RequestParam(name="taskDesc", required=true) String taskDesc,
			@RequestParam(name="taskStatus", required=true) String  taskStatus,
			Model model)
	{
		if(loggedinuser==null)
		{
			return "redirect:/login";
		}
		User usr=userservice.updateTaskStatus(loggedinuser,taskDesc,taskStatus);
//		model.addAttribute("user",usr);
//		model.addAttribute("task",new Task());
//		model.addAttribute("message","Welcome "+usr.getFirstName() + " "+ usr.getLastName());
//		
		return "redirect:/tasks";
	}
	
	
	
}
