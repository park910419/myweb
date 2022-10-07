package com.syp.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConn {

	private static OracleConn my = new OracleConn();
	private Connection conn;
	
	private OracleConn() {
		oracleConn();	
	}
	
	public static OracleConn getInstance() {
		return my;
	}
	
	private void oracleConn(){
		
		Properties pro = new Properties();
		String path = OracleConn.class.getResource("database.properties").getPath();
//		System.out.println(path);
		
		try {
			path = URLDecoder.decode(path, "utf-8");
			pro.load(new FileReader(path));
			
			String driver = pro.getProperty("driver");
			String url = pro.getProperty("url");
			String id = pro.getProperty("id");
			String pw = pro.getProperty("pw");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("오라클 연결 완료");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
		public Connection getConn(){
		return conn;
	}

}