package com.syp.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import oracle.jdbc.OracleTypes;

import javax.servlet.http.HttpServletRequest;
import com.syp.common.OracleConn;

public class MemberDao_SQL {

	private final Connection conn = OracleConn.getInstance().getConn();
	PreparedStatement stmt = null;
	
	public Map<String, String> loginProc(String id, String pw) {
//		String[] status = new String[2]; //로그인 실패
		Map<String, String> map = new HashMap<String, String>();
		
		String sql = "select * from member where id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				if(rs.getString("pw").equals(pw)){
//					status[0] = "ok"; //로그인 성공(패스워드 일치)
//					status[1] = rs.getString("name");
					map.put("login", "ok");
					map.put("name", rs.getString("name"));
				} else{
//					status[0] = "pwFail"; //로그인 실패(패스워드 불일치)
					map.put("login", "pwFail");
				} 
			} 	else {
//				status[0] = "fail";
				map.put("login", "fail");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//resourceClose();
		}

		return map;
	}

	private void resourceClose() {
		//자원반납
		try {
			stmt.close();
			conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public int insertMember(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");

		//취미
		String[] hobby = request.getParameterValues("hobby");
		String hobby_str = new String();
		for(int i=0; i<hobby.length; i++){
			hobby_str += hobby[i];
			if(i != hobby.length-1){
			hobby_str += ",";
			} 
		} 

		String email = request.getParameter("eid") + "@" + request.getParameter("domain");
		String intro = request.getParameter("intro");

		
		Statement stmt;
		int rs = 0;
		
		try {
			stmt = conn.createStatement();
			/*
			String sql = "INSERT INTO member(id, pw, name, gender, hobby, email, intro)"
						 +" VALUES ('" + id + "','" + pw + "','" + name + "','" + gender + "','" + hobby_str + "','" + email + "','" + intro + "' )";
			System.out.println(sql);*/
			
			String sql = String.format("INSERT INTO member" +
					"(id, pw, name, gender, hobby, email, intro)" + 
					"values('%s','%s','%s','%s','%s','%s','%s')",
					id, pw, name, gender, hobby_str, email, intro);
			
			System.out.println(sql);
			
			rs = stmt.executeUpdate(sql);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return rs;
	}

	public int selectById(String id) {
		CallableStatement stmt = null;
		int rs = 0;
		String sql = "call p_idDoubleCheck(?, ?)";
		try {
			stmt = conn.prepareCall(sql);
			stmt.setNString(1, id);
			stmt.registerOutParameter(2, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			rs = stmt.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
}
