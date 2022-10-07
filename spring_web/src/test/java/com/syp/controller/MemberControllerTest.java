package com.syp.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.syp.dto.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
								 "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class MemberControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger("MemberControllerTest.class");
	
	@Autowired
	private WebApplicationContext wac ;
	
	private MockMvc mockMvc ; //요청,응답을 처리
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		log.info("mockMvc setup..."); 
	}
	
	@Test
	public void test() {
		
		try {
			String rs = mockMvc.perform(MockMvcRequestBuilders.post("/login")
						.param("id", "young").param("pw", "11111"))
						.andReturn().getModelAndView().getViewName();
			log.info(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//RestController
	@Test
	public void test2() {
		Member member = new Member();
		member.setId("young");
		member.setPw("11111");
		member.setName("박세영");
		member.setGender("F");
		
		String jsonStr = new Gson().toJson(member);
		log.info(jsonStr);
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/ex/member").
							contentType(MediaType.APPLICATION_JSON).
							content(jsonStr)).andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
