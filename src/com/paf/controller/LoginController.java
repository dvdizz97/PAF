package com.paf.controller;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.paf.dao.*;
import com.paf.model.Member;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/Login")
public class LoginController implements LoginControllerImpl {

	MemberDao dao;
	Member mem;
	
	@Override
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public void login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
	
		String message = "", link = "";
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml")) {
			dao=(MemberDao)ctx.getBean("mdao");
			int error = dao.loginValidate(username, password);
			if (error == 0) {
				message = "Login incorrect!!!";
				link = "http://localhost:8081/Paf_assignment/login.jsp?message="+message;
				redirect(link, request, response);
			}
			else if(error == -99){
				message = "Something went wrong";
				link = "http://localhost:8081/Paf_assignment/login.jsp?message="+message;
				redirect(link, request, response);
			}
			else {
				link = "http://localhost:8081/Paf_assignment/StepBy.jsp?username="+username;
				redirect(link, request, response);
			}
		}
		catch (Exception e) {
			message = "Something went wrong";
			link = "http://localhost:8081/Paf_assignment/login.jsp?message="+message;
			System.out.println("Error: " + e);
		}
		
	}
	
	@Override
	@RequestMapping(value = "/redirect", method = RequestMethod.POST)
	public void redirect(String link, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
       
		try{
        	response.sendRedirect(link);
        }
        catch (Exception exp) {
            System.out.println("Error: " + exp);
        }
		
	}
	
}
