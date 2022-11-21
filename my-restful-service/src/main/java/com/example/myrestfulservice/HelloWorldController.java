package com.example.myrestfulservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
  //http status code : get,post,delete 요청시 코드도 같이리턴 (200,400,500..)

  //GET
  // /hello-world (uri : end-point)
  @GetMapping(path = "/hello-world")
  public String helloWorld() { return "Hello World"; }

  // hello-world-bean/path-variable/{name}
  @GetMapping(path = "/hello-world-bean/path-variable/{name},{major}")
  public HelloWorldBean helloWorldBean(@PathVariable String name, @PathVariable String major) {
    return new HelloWorldBean(String.format("Hello World, %s, %s", name, major));
  }

  @GetMapping("/add-api/{num1},{num2}")
  public HelloWorldBean add(@PathVariable int num1, @PathVariable int num2) {
    return new HelloWorldBean(String.format("%d + %d = %d", num1, num2, num1+num2));
  }
  @GetMapping("/sub-api/{num1},{num2}")
  public HelloWorldBean sub(@PathVariable int num1, @PathVariable int num2) {
    return new HelloWorldBean(String.format("%d - %d = %d", num1, num2, num1-num2));
  }
  @GetMapping("/mul-api/{num1},{num2}")
  public HelloWorldBean mul(@PathVariable int num1, @PathVariable int num2) {
    return new HelloWorldBean(String.format("%d * %d = %d", num1, num2, num1*num2));
  }
  @GetMapping("/div-api/{num1},{num2}")
  public HelloWorldBean div(@PathVariable int num1, @PathVariable int num2) {
    return new HelloWorldBean(String.format("%d / %d = %d", num1, num2, num1/num2));
  }

}

