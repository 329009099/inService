package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.result.MockMvcResultMatchers;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml",
		"classpath:dispatcher-servlet.xml"})
@WebAppConfiguration
public class NoUserCenterControllerTest  {
	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mvc;
	
	@Before
	public void setUp() {
		
		this.mvc=MockMvcBuilders.webApplicationContextSetup(wac).build();
	}

	//@Test
	public void testfindCoinLog() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/nouserCenter/findCoinLog")
						.param("userId", "48")
						)
				.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	//@Test
	public void testsignIn() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/nouserCenter/signIn")
						.param("userId", "48")
						)
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	//@Test
	public void testfindInvolvement() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/nouserCenter/findInvolvement")
						.param("userId", "134762")
						.param("status", "3")
						)
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	//@Test
	public void testcash() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/nouserCenter/cash")
						.param("alipay", "a@qq.com")
						.param("password", "123456")
						.param("cash", "2.31")
						.param("userId", "134762")
						)
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	
	//@Test
	public void testcoinToCash() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/nouserCenter/coinToCash")
						.param("userId", "134762")
						.param("coin", "300")
						)
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	@Test
	public void testfindCashLog() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/nouserCenter/findCashLog")
						.param("userId", "134762")
						)
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	
}
