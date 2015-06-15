package com.xxx.rpc.sample.app;

import com.xxx.rpc.client.RpcProxy;
import com.xxx.rpc.sample.client.HelloService;
import com.xxx.rpc.sample.client.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class HelloServiceTest {

	@Autowired
	private RpcProxy rpcProxy;

	@Test
	public void helloTest1() {
		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello("World");
		Assert.assertEquals("Hello! World", result);
	}

	@Test
	public void helloTest2() {
		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello(new Person("Yong", "Huang"));
		Assert.assertEquals("Hello! Yong Huang", result);
	}
}
