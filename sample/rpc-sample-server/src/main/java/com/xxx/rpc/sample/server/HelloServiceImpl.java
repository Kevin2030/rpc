package com.xxx.rpc.sample.server;

import com.xxx.rpc.sample.client.HelloService;
import com.xxx.rpc.sample.client.Person;
import com.xxx.rpc.server.RpcService;

/*指定远程接口*/
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "Hello! " + name;
	}

	@Override
	public String hello(Person person) {
		return "Hello! " + person.getFirstName() + " " + person.getLastName();
	}
}
