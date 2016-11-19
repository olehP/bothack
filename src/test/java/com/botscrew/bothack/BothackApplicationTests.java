package com.botscrew.bothack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.botscrew.bothack.messages.MessageKey;
import com.botscrew.bothack.messages.MessagesHolder;
import com.botscrew.bothack.messages.Style;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BothackApplication.class)
@WebAppConfiguration
public class BothackApplicationTests {
	@Autowired
	private MessagesHolder holder;

	@Test
	public void contextLoads() {
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.NORMAL));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.NORMAL));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.NORMAL));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.NORMAL));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.NORMAL));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.NORMAL));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.RAP));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.RAP));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.RAP));
		System.out.println(holder.getRandomMessage(MessageKey.WELCOME, Style.RAP));

	}

}
