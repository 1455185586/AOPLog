package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopLogController {
    @GetMapping("/aoptest")
    public String aVoid(){
        return "这是李兴振的第一个AOP编程Demo";
    }
}
