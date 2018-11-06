package org.atomsk.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberTests {
	
	@Setter(onMethod_=@Autowired)
	private PasswordEncoder pwEncoder;
	
	@Setter(onMethod_=@Autowired)
	private DataSource ds;
	
	@Test
	public void testInsertAuth() {
		String sql = "insert into tbl_member_auth (userid,auth) values (?,?)";
		for (int i = 9; i < 10; i++) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "testuser"+i);
				pstmt.setString(2, "ROLE_ADMIN");
				
				log.info(pstmt.executeUpdate());
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if (pstmt != null) {try {pstmt.close();}catch(Exception e) {}}
				if (con != null) {try {pstmt.close();}catch(Exception e) {}}	
				}
				
			}
	}
	
	@Test
	public void testInserMember() {
		
		log.info(ds);
		
		String sql = "insert into tbl_member (userid,userpw,username) values (?,?,?)";
		
		for (int i = 0; i < 50; i++) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "55user"+i);
				pstmt.setString(2, pwEncoder.encode("pw"+i));
				pstmt.setString(3, "사용자"+i);
				
				log.info(pstmt.executeUpdate());
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if (pstmt != null) {try {pstmt.close();}catch(Exception e) {}}
				if (con != null) {try {pstmt.close();}catch(Exception e) {}}	
				}
				
			}
			
		}
		
	
	
	@Test
	public void test1() {
		log.info(pwEncoder);
		
		String enPw = pwEncoder.encode("member");
		log.info(enPw);
		
		boolean result = pwEncoder.matches(enPw, "$2a$10$IZkxqs.RvfYQwIUWCW5Do.OQqT8A6nktYHKKlKLcMcRNR9a0nmuOC");
		
		log.info("result " + result);
	}

}
