package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j //Logger logger = LoggerFactory.getLogger(getClass()); 것의 어노테이션임
public class HomeController {
    public static void main(String[] args) {

    }

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
