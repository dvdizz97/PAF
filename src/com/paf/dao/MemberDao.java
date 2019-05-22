package com.paf.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.paf.model.*;

public class MemberDao {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
		
	}

	public int registerMember(Member m){
		
		try {
			String query="INSERT INTO `member2`(`Name`, `Password`, `Type`, `Address`, `Mail_Address`, `Contact_Number`) VALUES ('"+m.getName()+"','"+m.getPassword()+"', '"+m.getType()+"' , '"+m.getAddress()+"' , '"+m.getMail()+"' , '"+m.getContactNumber()+"')";
			return jdbcTemplate.update(query);
		}
		catch(Exception e) {
			System.out.println("Error: " + e);
			return -99;
		}
		
	}

	public int loginValidate(String username, String password) {
		
		try {
			return this.jdbcTemplate.queryForInt("SELECT count(*) FROM `member2` WHERE Name = '"+username+"' AND Password = '"+password+"'");
		}
		catch(Exception e) {
			System.out.println(e);
			return -99;
		}
		
	}

	public Member findMember(String name) {
		
		Member mem;
		try {
			int mid = Integer.parseInt((String) this.jdbcTemplate.queryForObject("select ID from member2 where Name = '"+name+"'", String.class));
			String password = (String) this.jdbcTemplate.queryForObject("select Name from member2 where Name = '"+name+"'", String.class);
			String mtype = (String) this.jdbcTemplate.queryForObject("select Type from member2 where Name = '"+name+"'", String.class);
			String address	 = (String) this.jdbcTemplate.queryForObject("select Address from member2 where Name = '"+name+"'", String.class);
			String mail = (String) this.jdbcTemplate.queryForObject("select Mail_Address from member2 where Name = '"+name+"'", String.class);
			int contactNumber = Integer.parseInt((String) this.jdbcTemplate.queryForObject("select Contact_Number from member2 where Name = '"+name+"'", String.class));
			mem = new Member(mid, name, password, mtype, address, mail, contactNumber);
		}
		catch(Exception e) {
			System.out.println("Error: " + e);
			mem = null;
		}
		return mem;
		
	}

	public int updateMember(Member e){
		
		try {
			String query="update member2 set Type='"+e.getType()+"',Address='"+e.getAddress()+"', Mail_Address='"+e.getMail()+"', Contact_Number='"+e.getContactNumber()+"' where Name='"+e.getName()+"' ";
			return jdbcTemplate.update(query);
		}
		catch(Exception z) {
			System.out.println(z);
			return -99;
		}
		
	}

	public int deactivate(String username){
		
		try {
			String query="Delete from member2 where Name='"+username+"'";
			return jdbcTemplate.update(query);
		}
		catch(Exception z) {
			System.out.println(z);
			return -99;
		}
		
	}
}
