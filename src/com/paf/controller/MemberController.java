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
@RequestMapping("/member")

public class MemberController implements MemberControllerImpl {
	
	Member mem;
	MemberDao dao;
	
	@Override
	@RequestMapping(value = "/Register", method = RequestMethod.POST)
	public Member register(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("type") String type, @RequestParam("address") String address, @RequestParam("mailAddress") String mailAddress, @RequestParam("contactNumber") int contactNumber) {
		
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml")) {
			int id = 1, status;
			String link;
			
			dao=(MemberDao)ctx.getBean("mdao");
			mem = new Member(id, username, password, type, address, mailAddress, contactNumber);
			status=dao.registerMember(mem);
			
			if(status == -99) {
				System.out.println("Member failed to register");
				link = "http://localhost:8081/Paf_assignment/RegisterFail.jsp";
			}
			else{
				System.out.println("Member registered");
				link = "http://localhost:8081/Paf_assignment/RegisterSuccess.jsp?username="+username;
			}
			
			redirect(link, request, response);
			return mem;
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
		
	}

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	@Override
	public Member myProfile(HttpServletRequest request, HttpServletResponse response,  @RequestParam("username") String username) {
	
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml")) {
			String link;
			dao=(MemberDao)ctx.getBean("mdao");
			mem = dao.findMember(username);
			link = "http://localhost:8081/Paf_assignment/myprofile.jsp?username="+mem.getName()+"&type="+mem.getType()+"&address="+mem.getAddress()+"&mailAddress="+mem.getMail()+"&contactNumber="+mem.getContactNumber();
			redirect(link, request, response);
			return mem;
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
			return mem;
		}
		
	}
	
	@Override
	@RequestMapping(value = "/updateForm", method = RequestMethod.GET)
	public Member generateUpdateForm(@RequestParam("username") String username, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml")) {
			dao=(MemberDao)ctx.getBean("mdao");
			mem = dao.findMember(username);
			String link, message;
			if(mem == null) {
				System.out.println("Something went wrong");
				message = "Something went wrong";
				link = "http://localhost:8081/Paf_assignment/UpdateForm.jsp?message="+message;
			}
			else{
				System.out.println("Member found");
				String number = Integer.toString(mem.getContactNumber());
				link = "http://localhost:8081/Paf_assignment/UpdateForm.jsp?type="+mem.getType()+"&address="+mem.getAddress()+"&mailAddress="+mem.getMail()+"&contactNumber="+number+"&password="+mem.getPassword();
			}
			redirect(link, request, response);
			return mem;
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
			String message = "Something went wrong";
			String link = "http://localhost:8081/Paf_assignment/UpdateForm.jsp?message="+message;
			redirect(link, request, response);
			return null;
		}
		
	}

	@Override
	@RequestMapping(value = "/updateMember", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response,  @RequestParam("username") String username,  @RequestParam("type") String type, @RequestParam("address") String address, @RequestParam("mailAddress") String mailAddress, @RequestParam("contactNumber") int contactNumber) throws ServletException, IOException {
		
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml")) {
			String link = "";
			int status;
			
			dao=(MemberDao)ctx.getBean("mdao");
			mem = new Member(1, username, "null", type, address, mailAddress, contactNumber);
			status=dao.updateMember(mem);
			
			if(status == -99) {
				System.out.println("Member failed to update");
			}
			else{
				System.out.println("Member updated");
				link = "http://localhost:8081/Paf_assignment/UpdateSuccess.jsp";
			}
			redirect(link, request, response);
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
	}

	@Override
	@RequestMapping(value = "/deactivate", method = RequestMethod.GET)
	public void deactivate(@RequestParam("username") String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml")) {
			dao=(MemberDao)ctx.getBean("mdao");
			String link = "";
			String message = "Your account is deactivated";
			int status = dao.deactivate(username);
			if(status == -99) {
				System.out.println("Member failed to deactivate");
			}
			else{
				System.out.println("Member deactivated");
				link = "http://localhost:8081/Paf_assignment/login.jsp?message="+message;
			}
			redirect(link, request, response);
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
	}
	
	@Override
	@RequestMapping(value = "/redirect", method = RequestMethod.POST)
	public void redirect(String link, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
      
		try{
        	response.sendRedirect(link);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
		
	}
	
}
