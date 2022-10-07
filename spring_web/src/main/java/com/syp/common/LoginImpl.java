package com.syp.common;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;


@WebListener
public class LoginImpl implements HttpSessionBindingListener {
	private String id;
	private String name;
	public static int total_user = 0;

	public LoginImpl() {	
	}
	
	public LoginImpl(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
    public static int getTotal_user() {
		return total_user;
	}

	public static void setTotal_user(int total_user) {
		LoginImpl.total_user = total_user;
	}

    public void valueBound(HttpSessionBindingEvent event)  { 
    	//세션 저장 시 호출
    	System.out.println("로그인");
    	++total_user;
    	System.out.println("현재 로그인 인원:" + total_user);
    }

    public void valueUnbound(HttpSessionBindingEvent event)  { 
    	//세션 소멸시 호출
    	System.out.println("로그아웃");
    	total_user--;
    	System.out.println("현재 로그인 인원:" + total_user);
    }
	
}
