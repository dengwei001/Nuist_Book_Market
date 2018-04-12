package com.nuist.bookMarket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuist.bookMarket.mapper.ParamAdminMapper;
import com.nuist.bookMarket.mapper.SchoolBookMapper;
import com.nuist.bookMarket.mapper.UserMapper;
import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.schedule.SynchronousOrder;
import com.nuist.bookMarket.service.JSONArrayService;
import com.nuist.bookMarket.service.MessageService;
import com.nuist.bookMarket.util.MD5Utils;
import com.nuist.bookMarket.util.SequenceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookMarketApplicationTests {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SchoolBookMapper schoolBookMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SequenceUtils sequenceUtils;
	@Autowired
	private ParamAdminMapper paramAdminMapper;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	JSONArrayService jsonArrayService;
	@Autowired
	private SynchronousOrder synchronousOrder;
	@Autowired
	private MessageService messageService;


	@Test
	public void contextLoads() {
	}





	@Test
	public void a() throws SQLException {
		Map mapResult = new HashMap<>();
		PageHelper.startPage(2,5,true);
		List list = userMapper.selectAll() ;//查询
		User user = userMapper.selectByUserName("dengwei");
		logger.debug(user.getName());
		// 取用户列表
		for(Object item : list) {
			System.out.println(item.toString());
		}
//		List list = custMapper.selectByPrimaryKey(1);
		System.out.println(list.toString());
		mapResult.put("totalPage", ((Page)list).getPages());
		mapResult.put("result", "success");
		mapResult.put("detail", list);
		PageInfo pageInfo = new PageInfo(list);
		logger.debug(pageInfo.toString());
		logger.debug(list.toString());
		logger.debug(mapResult.toString());
		logger.debug(String.valueOf(pageInfo.getTotal()));
		logger.debug(String.valueOf(dataSource.getConnection()));
	}
	@Test
	public void testSchoolBookMapper(){
		Map param = new HashMap();
		param.put("COLLEGE","计算机与软件学院");
		param.put("SPECIALTY","物联网工程");
		List list=schoolBookMapper.querySchoolBook(param);
		logger.debug(schoolBookMapper.querySchoolBook(param).toString());
	}

	@Test
	public void testSquence() throws Exception {
		logger.debug("CollegeCode"+sequenceUtils.getCollegeCode());
		logger.debug("SchoolBookId"+sequenceUtils.getBookId());
		logger.debug("SpecialtyCode"+sequenceUtils.getSpecialtyCode());
	}

	@Test
	public void testmd5() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String s = "123456";
		logger.debug(MD5Utils.md5("123456"));
	}

	@Test
	public void testQueryMap(){
		String collegeCode = "171230100000055";
		logger.debug(paramAdminMapper.selectCollegeByCode(collegeCode).toString());
	}

	@Test
	public void testJSON(){
		String collegeCode = "171230100000055";
		Map map = paramAdminMapper.selectCollegeByCode(collegeCode);
		logger.debug(JSONObject.toJSON(map).toString());
		logger.debug(JSON.toJSON(map).toString());
		logger.debug(JSONObject.toJSONString(map));
		logger.debug(JSONArray.toJSON(map).toString());
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(map);
		logger.debug(jsonArray.toJSONString());
		logger.debug(jedisPool.getResource().set("a","a"));

	}

	@Test
	public void testJedisPool() {
//		Jedis jedis = jedisPool.getResource();
		Jedis jedis = new Jedis("120.79.93.1", Integer.parseInt("7001"));
		jedis.auth("D@www19951006");
		logger.debug(jedis.get("dengwei"));
		List list = schoolBookMapper.selectAll();
		jedis.lpush("testList", String.valueOf(list));
		logger.debug(String.valueOf(jedis.lrange("testList",0,-1)));
//		jedis.hmset("testMap", (Map<String, String>) list.get(0));
		for (int i=0;i<list.size();i++){
			jedis.lpush("testList", String.valueOf(list.get(i)));
		}
		jedis.hset("testMap2","1234", String.valueOf(list.get(0)));
	}

	@Test
	public void testJSONArray(){
		List book = schoolBookMapper.selectAll();
		JSONArray jsonBook = new JSONArray(book);
		logger.debug(String.valueOf(jsonBook));
		//删除
		jsonBook = jsonArrayService.del(jsonBook,"BOOK_ID","180107100000071");
		Jedis jedis = jedisPool.getResource();
		jedis.set("userId", String.valueOf(jsonBook));
		//更新
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("BOOK_NAME","001");
		jsonObject.put("BOOK_ID","123132131321");
		JSONArray jsonArrayUp = jsonArrayService.update(jsonBook,jsonObject,"BOOK_ID");
		logger.debug(String.valueOf(jsonArrayUp));
		logger.debug(String.valueOf(jsonBook));
		//查询
		JSONObject jsonObject1 = jsonArrayService.query(jsonBook,"BOOK_ID","123132131321");
		logger.debug(String.valueOf(jsonObject1));
		String gou = jedis.get("1234");
		logger.debug(gou);
		JSONArray gouJSON;
		if (gou!=null){
			gouJSON = JSONArray.parseArray(gou);
			gouJSON.add(jsonObject);

		}else {
			gouJSON = new JSONArray();
			gouJSON.add(jsonObject);
		}
		logger.debug(String.valueOf(gouJSON));
	}

	@Test
	public void testSynchronousOrder() throws Exception {
		synchronousOrder.importOrderFromRedisToSql();
	}


	@Test
	public void testCreatCode(){
		String code = messageService.createCode();
		logger.debug(code);
		logger.debug(String.valueOf(messageService.validate(code)));
		logger.debug(messageService.sendTplSms("111","JSM42172-0002",code));
	}


}
