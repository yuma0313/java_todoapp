package com.example.demo.App;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;
	
	//userListのアドレスにアクセスした際にGetメソッドを実行。
    @GetMapping("/userList")
    public String getUserList(Model model) {
    	List<User> userList = userRepo.findAll();
    	
    	model.addAttribute("userList", userList);

        //template配下のファイル名を指定することでViewを呼び出せる。     
        return "userList";
    }
    
    @GetMapping("/login")
    public String getSignUp(Model model) {
    	return "login";
    }
    
    @PostMapping("/login")
    public String postSignUp(Model model) {
    	
    	return "redirect:/userList";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "signup_form";
    }
    
    @PostMapping("/process_register")
    public String processRegister(@Validated @ModelAttribute("user") User user, BindingResult result) {
    	/* 入力チェック*/
        if (result.hasErrors()) {
            /* NG:ユーザー登録画面に戻る*/
            return "signup_form";
        }
    	
        userService.create(user);
        
        return "login";
    }
    
    //テスト用
    @GetMapping("")
    public String viesHomePage(Model model) {
    
        return "index";
    }
}
