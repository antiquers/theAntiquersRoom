package com.theantiquersroom.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.theantiquersroom.myapp.domain.LoginDTO;
import com.theantiquersroom.myapp.domain.UserDTO;
import com.theantiquersroom.myapp.service.UserService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Controller
public class LoginController {

	
	public static final String authKey="__AUTH_ANTIQUE__";
	
	@Setter(onMethod_= {@Autowired})
    private UserService service;
	
	@GetMapping("/login")
    public void login() {	// 로그인 페이지로 이동
        log.debug("login() invoked.");

    } //login
	
	@PostMapping("/loginCheck")	// 로그인 실행
    public String login(
    		LoginDTO dto, Model model
    		) throws Exception {
        log.debug("login({}) invoked.", dto);
        
        UserDTO user=this.service.login(dto);
        log.info("\t+ user: {}", user);
        
        if(user != null) {
        	model.addAttribute(LoginController.authKey, user);
        	return "/home";
        }
        
        return "/login";
    } //login
}
