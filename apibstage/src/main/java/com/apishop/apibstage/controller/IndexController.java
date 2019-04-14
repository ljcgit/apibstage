package com.apishop.apibstage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@RestController
public class IndexController {

    @PostMapping("/index")
    public String index(@RequestBody  Map<String,Object> maps){
        System.out.println(maps.get("k1"));
        System.out.println(maps.get("k2"));
        return "主页......";
    }
}
