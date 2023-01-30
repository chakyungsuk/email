package com.example.demo.emailController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.emailSend.Email_Service;

@Controller
@RequestMapping(value="/email")
public class Email_Controller {

	@Autowired Email_Service emailService;
	
	// 이메일 보내기 
	@SuppressWarnings("static-access")
	@GetMapping("/send")
	public void emailSend() {
		emailService.sendMail();
	}
	
//	@GetMapping("/receive")
//	public void receiveemail() throws Exception {
//		emailService.receiveMail();
//	}
	
}
