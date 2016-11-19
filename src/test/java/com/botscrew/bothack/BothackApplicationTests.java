package com.botscrew.bothack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.botscrew.bothack.messages.MessagesHolder;
import com.botscrew.bothack.service.impl.ApiAiServiceImpl;
import com.botscrew.bothack.service.impl.EbayServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BothackApplication.class)
@WebAppConfiguration
public class BothackApplicationTests {
	@Autowired
	private MessagesHolder holder;
	@Autowired
	private ApiAiServiceImpl ai;
	@Autowired
	private EbayServiceImpl ebay;

	@Test
	public void contextLoads() {
		System.out.println(ebay.getProducts("iphone", 52.4963251, 13.4228209, 10, 2000, 1, 10));
	}

}
