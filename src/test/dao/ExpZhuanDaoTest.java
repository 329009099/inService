package test.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.suyin.other.mapper.OtherMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml",
		"classpath:dispatcher-servlet.xml"})
public class ExpZhuanDaoTest  extends AbstractJUnit4SpringContextTests  {
	
	@Autowired
	private OtherMapper mapper;
	
	@Test
	public void test2() throws Exception {
		List<Map<String,String>> re=this.mapper.get1();
		JSONArray jsa=new JSONArray();
		for(Map<String,String> m:re) {
			JSONObject jso=new JSONObject();
			jso.put("proname", m.get("name"));
			jso.put("proid", m.get("id"));
			List<Map<String,String>> re1=this.mapper.get2(String.valueOf(m.get("id")));
			JSONArray jsa1=new JSONArray();
			for(Map<String,String> kk:re1) {
				JSONObject jso1=new JSONObject();
				jso1.put("cityname", kk.get("name"));
				jso1.put("cityid", kk.get("id"));
				jsa1.add(jso1);
			}
			jso.put("citys", jsa1);
			jsa.add(jso);
		}
		
		System.out.println(jsa.toString());
	}
}
