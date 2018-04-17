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
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml",
		"classpath:dispatcher-servlet.xml"})
@WebAppConfiguration
public class ExpZhuanControllerTest {
	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mvc;
	
	@Before
	public void setUp() {
		
		this.mvc=MockMvcBuilders.webApplicationContextSetup(wac).build();
	}
	//@Test
	public void test2() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/expz/findTwo")
						)
						//.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
				.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	@Test
	public void testJoinExp() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/expz/joinExp")
						.param("userPhone", "18705164891")
						.param("expId", "23")
						.param("regtype", "1")
						.param("moduleType","0")
						)
						//.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
				.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	//@Test
	public void testClickExp() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/expz/clickExpTaskB")
						.param("expId", "11")
						.param("userId", "48")
						.param("orderId", "7")
						)
						//.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	//@Test
	public void testsubmitExpTaskA() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/expz/submitExpTaskA")
						.param("userId", "51")
						.param("orderId", "1")
						.param("imageUrl", "aaaa")
						.param("attach", "24172")
						)
						//.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	//@Test
	public void testsubmitExpTaskAForm() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/expz/submitExpTaskFormA")
						.param("data", "[{\"did\":\"1\",\"pdid\":\"2\",\"textValue\":\"abc\",\"userId\":\"1\",\"expId\":\"22\",\"moduleType\":\"1\"}"
								+ ",{\"did\":\"2\",\"pdid\":\"3\",\"textValue\":\"erg\",\"userId\":\"1\",\"expId\":\"22\",\"moduleType\":\"1\"}"
								+ "]")
								.param("orderId", "4")
						)
						//.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	//@Test
	public void testfindExpForm() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/expz/findExpForm")
								.param("expId", "22")
						)
						//.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
						.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
}
