package com.syp.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;

public class EncodingFilter implements Filter {
	
	private String encoding = null;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.encoding = fConfig.getInitParameter("encoding"); //"encoding" => web.xml의 init-param name
		System.out.println(fConfig.getFilterName()+"필터가 시작되었습니다.");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if(encoding != null) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}

}
