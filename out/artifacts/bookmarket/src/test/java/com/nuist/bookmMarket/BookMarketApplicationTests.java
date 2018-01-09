package com.nuist.bookmMarket;

import com.nuist.bookmMarket.mapper.CustMapper;
import com.nuist.bookmMarket.model.Cust;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookMarketApplicationTests {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustMapper custMapper;
	@Autowired
	DataSource dataSource;

	@Test
	public void contextLoads() {
	}

	@Test
	public void a() throws SQLException {
		List list = custMapper.selectAll();
		logger.debug(list.toString());
		logger.debug(String.valueOf(dataSource.getConnection()));
	}
}
