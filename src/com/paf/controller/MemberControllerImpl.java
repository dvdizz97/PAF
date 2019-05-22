package com.paf.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paf.model.*;

public interface MemberControllerImpl {
	public Member register(HttpServletRequest request, HttpServletResponse response, String username, String password, String type, String address,  String mailAddress, int contactNumber);
	public void update(HttpServletRequest request, HttpServletResponse response, String username, String type, String address, String mailAddress, int contactNumber)throws ServletException, IOException;
	public void redirect(String link, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	public Member myProfile(HttpServletRequest request, HttpServletResponse response, String username);
	public Member generateUpdateForm(String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	public void deactivate(String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
