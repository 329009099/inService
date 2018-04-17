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
public class OtherControllerTest {
	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mvc;
	
	@Before
	public void setUp() {
		
		this.mvc=MockMvcBuilders.webApplicationContextSetup(wac).build();
	}
	@Test
	public void testfindAdvs() throws Exception {
		MvcResult mr= 
				mvc.perform(
						MockMvcRequestBuilders.get("/advs/find")
						.param("type", "0")
						)
				.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	
}
