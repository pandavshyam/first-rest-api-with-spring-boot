package com.example.springboot.firstrestapi.helloworld;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller
@RestController
public class HelloWorldResource {

  @RequestMapping("/hello-world")
  public String helloWorld(){
    return "Hello World!";
  }

  @RequestMapping("/hello-world-path-param/{name}")
  public HelloWorldBean helloWorldPathParam(@PathVariable String name){
    return new HelloWorldBean(name);
  }
}
