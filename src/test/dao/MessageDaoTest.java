package test.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.suyin.common.mapper.MessageMapper;
import com.suyin.expzhuan.mapper.ExperienceTaskMapper;
import com.suyin.expzhuan.model.ExperienceTaskOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml",
		"classpath:dispatcher-servlet.xml"})
public class MessageDaoTest  extends AbstractJUnit4SpringContextTests  {
	
	@Autowired
	private MessageMapper mapper;
	
	//@Test
	public void test2() throws Exception {
		Map<String, String> map=new HashMap<String, String>();
		map.put("userId", "1");
		map.put("expId", "11");
		map.put("pattern", "你参加了【#】活动");
		this.mapper.addMessageForExpTask(map);
	}
	@Test
	public void test3() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", "1");
		System.out.println(this.mapper.findMessageForUserByPage(map));
	}
}
