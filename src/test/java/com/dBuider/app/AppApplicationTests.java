package com.dBuider.app;

import com.dBuider.app.Model.Category;
import com.dBuider.app.Model.Order;
import com.dBuider.app.Model.User;
import com.dBuider.app.Service.Interfaces.OrderService;
import com.dBuider.app.Service.Interfaces.ToolsService;
import com.dBuider.app.Service.Interfaces.UserDetailsService;
import com.dBuider.app.Service.TranslitService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppApplicationTests
{
	@Autowired
	private ToolsService toolsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private TranslitService translitService;

	@Test
	public void ordersTest()
	{
		Assert.assertNotEquals(orderService.getAllOrders().size(),0);

		List<Order> orders = orderService.getUncompletedOrders();
		Assert.assertNotEquals(orders.size(),0);
		for (Order order:orders)
		{
			Assert.assertEquals(false,order.isDone());
		}
	}

	@Test
	public void toolsTest()
	{
		Assert.assertNotEquals(toolsService.getTopTools().size(),0);
		Assert.assertNotEquals(toolsService.findTools("Makita mega").size(),0);
		Assert.assertNotEquals(toolsService.findTools("Электроинструменты","Перфораторы").size(),
				0);
		Assert.assertNotEquals(toolsService.getBrands().size(),0);
		Assert.assertNotEquals(toolsService.getCategories().size(),0);
		Assert.assertNotEquals(toolsService.findTools("Makita").size(),0);
		Assert.assertNotEquals(toolsService.findTools("maKita").size(),0);
	}

	@Test
	public void usersTest()
	{
		Assert.assertEquals("Vasya",userDetailsService.
				loadUserByUsername("Vasya").getUsername());
	}

	@Test
	public void dublicateTest()
	{
		userDetailsService.saveUser(new User("Vasya","123",
				"","","gmail","Vasya","Vasyavich"));
		userDetailsService.saveUser(new User("Bob","123",
				"","","mail","Vasya","Vasyavich"));
		toolsService.addCategory(new Category("Электроинструменты","Перфораторы"));
	}

	@Test
	public void notFoundTest()
	{
		Assert.assertEquals(0, toolsService.findTools("gg").size());
		Assert.assertEquals(0,toolsService.findTools("gg","gg").size());
	}

	@Test
	public void translitTest()
	{
		translit("Инструмент");
		translit("perforatori");
		translit("Штроборез");
		translit("Штробо33рез");
	}

	private void translit(String str)
	{
		Assert.assertEquals(translitService.toRus(str),
				translitService.toRus(translitService.toEng(str)));
		Assert.assertEquals(translitService.toEng(str),
				translitService.toEng(translitService.toRus(str)));
	}
}
