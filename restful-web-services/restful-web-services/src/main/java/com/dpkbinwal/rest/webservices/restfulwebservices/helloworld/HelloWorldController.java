package com.dpkbinwal.rest.webservices.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//rest api
@RestController
public class HelloWorldController {
	
	//hello-world return helloworld
//	method1-of mapping
//	@RequestMapping(method= RequestMethod.GET, path="/hello-world")
//	public String helloWorld() {
//		return "hello world";
//	}
//	
//	@GetMapping(path="/hello-world")
//	public String helloWorld() {
//		return "hello world1";
//	}
//	
//	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world1");
	}
	
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable( @PathVariable String name ) {
		
//		return new HelloWorldBean("hello world "+ name);
		return new HelloWorldBean(
				String.format("Hello world, %s", name)
				);
		
	}
}
