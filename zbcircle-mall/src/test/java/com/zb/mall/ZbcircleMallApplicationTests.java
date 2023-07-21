package com.zb.mall;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.mall.dao.VoucherMapper;
import com.zb.mall.pojo.Voucher;
import com.zb.mall.utils.RedisIdWorker;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.data.redis.core.StringRedisTemplate;


@SpringBootTest
class ZbcircleMallApplicationTests {

	@Test
	void contextLoads() throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("src/main/resources/generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("生成成功！");

	}

	long BEGIN_TIMESTAMP = 1679011200L;
	long COUNT_BITS = 32;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void Test2() throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
		LocalDateTime now= LocalDateTime.now();
		long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
		String keyPrefix = "555";
		long timestamp = nowSecond - BEGIN_TIMESTAMP ;
		System.out.println(nowSecond);
		System.out.println(timestamp);

		// 获取当前日期，精确到天
		String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		System.out.println(date);
		// 个悲剧当前的date ??
		long count =  stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
		// 拼接并返回
		System.out.println(timestamp << COUNT_BITS | count);


	}


	@Autowired
	private VoucherMapper voucherMapper;

	@Autowired
	private RedisIdWorker redisIdWorker;

	@Test
	@XxlJob("addSeckilltest")
	public void Test3() {
		System.out.println("haha");
		LocalDateTime startTime = LocalDateTime.now(); // 可以根据实际情况获取抢购开始时间

		LocalDateTime endTime = startTime.plusDays(3);
		Voucher voucher = new Voucher().builder().stock(100).beginTime(startTime).endTime(endTime)
				.rules("任意商品,满100减10").title("每周限定秒杀优惠券").createTime(LocalDateTime.now()).updateTime(LocalDateTime.now())
				.payValue(1000L).actualValue(100L).id(redisIdWorker.nextId("seckill")).build();


		voucherMapper.insert(voucher);

		// 保存秒杀信息
		stringRedisTemplate.opsForValue().set(RedisConstants.SECKILL_STOCK_KEY + voucher.getId(),voucher.getStock().toString());
	}

}

