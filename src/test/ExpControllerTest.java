package test;

import java.util.Date;
import java.util.Random;

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

import com.suyin.system.util.Tools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml",
"classpath:dispatcher-servlet.xml"})
@WebAppConfiguration
public class ExpControllerTest  {
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mvc;

    @Before
    public void setUp() {

        this.mvc=MockMvcBuilders.webApplicationContextSetup(wac).build();
    }
    /*	@Test
	public void test() throws Exception {
		MvcResult mr=  mvc.perform(MockMvcRequestBuilders.post("/unifiedLogin/setJedis").sessionAttr("loginUser", new LoginUser()))
				.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}
	@Test
	public void test1() throws Exception {
		MvcResult mr=  mvc.perform(MockMvcRequestBuilders.post("/unifiedLogin/getJedis").sessionAttr("loginUser", new LoginUser()))
				.andReturn();
		MockHttpServletResponse res= mr.getResponse();
		System.out.println(res.getContentAsString());
	}*/
    @Test
    public void test2() throws Exception {
        MvcResult mr=mvc.perform(MockMvcRequestBuilders.get("/exp/findTwo")).andReturn();
        MockHttpServletResponse res= mr.getResponse();
        System.out.println("###################"+res.getContentAsString());
    }
    
    /*
     * 获取随机字符
     */
    public static String getRandomString(int length){
        StringBuffer buffer=new StringBuffer("0123456789");
        StringBuffer sb=new StringBuffer();
        Random r=new Random();
        int range=buffer.length();
        for(int i=0;i<length;i++){
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }
    /**
     * 券号生成规则
     * @return 
     * @see
     */
    public String orderCode(){
        String result="";
        return result;

    }

    public static void main(String[] args)
    {
            String tel="18705164891";
            String ee="524406665219";
            System.out.println(ee.length());
            Long str=System.nanoTime();
            Tools.date2Str(new Date(), "yyyyMMdd");
//            for (int i = 0; i < 100000; i++ )
//            {
                
                System.out.println(ExpControllerTest.getRandomString(6)+"="+System.nanoTime());
//            }
    }
}
