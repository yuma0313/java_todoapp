package com.example.demo.App;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Config.CustomerUserDetails;
import com.example.demo.Entity.Task;
import com.example.demo.Entity.User;
import com.example.demo.Repository.TaskRepository;

@Controller
public class TaskController {
	@Autowired
	TaskRepository taskRepository;
	
	@GetMapping("/tasks")
	public String getTaskAll(Model model,@AuthenticationPrincipal CustomerUserDetails userDetails) {
		int userId = userDetails.getId();
		List<Task> task = taskRepository.findByUserId(userId);
				
		model.addAttribute("taskList",task);
		
		return "taskList";
	}
	
	@GetMapping("/create_task")
	public String getCreateTask(Task task, Model model,@AuthenticationPrincipal CustomerUserDetails userDetails) {
		//バリデーションエラーが出た場合データを保持した状態で表示する
		if (!model.containsAttribute("task")) {
	        model.addAttribute("task", new Task());
		}
		
		int userId = userDetails.getId();
		model.addAttribute("userid",userId);
		
		return "create_task";
	}
	
	@PostMapping("/create_task")
	public String postCreateTask(@Validated Task task, BindingResult bindingResult,@AuthenticationPrincipal CustomerUserDetails userDetails,User user, Model model, RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.task", bindingResult);
            attributes.addFlashAttribute("task", task);
			
			return "create_task";
        }
		//ログイン中ユーザーのIDを取得
		int userId = userDetails.getId();
		
		task.setId(task.getId());
		task.setTitle(task.getTitle());
		task.setDetail(task.getDetail());
		task.setEnd_time(task.getEnd_time());
		task.setUserId(userId);
		
		taskRepository.save(task);
		
		return "redirect:/tasks";
	}
	
	@GetMapping("/detail/{id}")
	public String getDetail(Model model,@PathVariable("id") Integer id) {
		//int taskId = Integer.parseInt(id);
		Optional<Task> task = taskRepository.findById(id);
		model.addAttribute("taskList",task.get());
		
		Calendar calendar = Calendar.getInstance();
		model.addAttribute("today",calendar);
		return "taskDetail";
	}
	
	@GetMapping("/detail/{id}/edit")
	public String getEdit(@PathVariable("id") int id, Model model) {
		Optional<Task> task = taskRepository.findById(id);
		model.addAttribute("task",task.get());
		
		return "taskEdit";
	}
	
	@PostMapping("/detail/{id}/update")
	public String postUpdateTask(@Validated Task task, BindingResult bindingResult,@AuthenticationPrincipal CustomerUserDetails userDetails,User user, Model model, @PathVariable("id") int id) {
//		if (bindingResult.hasErrors()) {
//			return "taskEdit";
//        }
		
		//ログイン中ユーザーのIDを取得
		int userId = userDetails.getId();
		
		task.setId(id);
		task.setTitle(task.getTitle());
		task.setDetail(task.getDetail());
		task.setEnd_time(task.getEnd_time());
		task.setUserId(userId);
		
		taskRepository.save(task);
		
		return "redirect:/tasks";
	}
	
	@GetMapping("/delete/{id}")
	public String postDelteTask(@PathVariable("id") int id) {
		taskRepository.deleteById(id);
		
		return "redirect:/tasks";
	}
}