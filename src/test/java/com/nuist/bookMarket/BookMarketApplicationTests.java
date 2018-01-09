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
import com.nuist.bookMarket.util.MD5Utils;
import com.nuist.bookMarket.util.SequenceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


	@Test
	public void contextLoads() {
	}



	@Test
	public void a() throws SQLException {
		Map mapResult = new HashMap<>();
		PageHelper.startPage(2,5,true);
		List<Map> list = userMapper.selectAll() ;//查询
		User user = userMapper.selectByUserName("dengwei");
		logger.debug(user.getName());
		// 取用户列表
		for(Map item : list) {
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
		String s = "888888";
		logger.debug(MD5Utils.md5("888888"));
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

	}
}
