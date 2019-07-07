package com.dBuider.app;

import com.dBuider.app.Model.Brand;
import com.dBuider.app.Model.Order;
import com.dBuider.app.Model.Tool;
import com.dBuider.app.Model.User;
import com.dBuider.app.Service.Interfaces.OrderService;
import com.dBuider.app.Service.Interfaces.ToolsService;
import com.dBuider.app.Service.Interfaces.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.Date;
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

	@Test
	public void ordersTest()
	{
		Assert.assertNotEquals(orderService.getAllOrders().size(),0);

		List<Order> orders = orderService.getUncompletedOrders();
		Assert.assertNotEquals(orders.size(),0);
		for (Order order:orders)
		{
			Assert.assertEquals(order.isDone(),false);
		}
	}

	@Test
	public void toolsTest()
	{
		Assert.assertNotEquals(toolsService.getTopTools().size(),0);
		Assert.assertNotEquals(toolsService.findTools("Makita mega").size(),0);
		Assert.assertNotEquals(toolsService.findTools("perforatori",null).size(),
				0);
		Assert.assertNotEquals(toolsService.getBrands().size(),0);
		Assert.assertNotEquals(toolsService.getCategories().size(),0);
		Assert.assertNotEquals(toolsService.getSubCategories().size(),0);
		Assert.assertNotEquals(toolsService.findTools("Makita").size(),0);
		Assert.assertNotEquals(toolsService.findTools("maKita").size(),0);
	}

	@Test
	public void usersTest()
	{
		Assert.assertEquals(userDetailsService.loadUserByUsername("Vasya").getUsername(),
				"Vasya");
	}

	@Test
	public void dublicateTest()
	{
		userDetailsService.saveUser(new User("Vasya","123",
				"","","gmail","Vasya","Vasyavich"));
		userDetailsService.saveUser(new User("Bob","123",
				"","","mail","Vasya","Vasyavich"));
	}
}
