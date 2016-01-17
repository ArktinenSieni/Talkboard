package talkboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mcsieni on 15.1.2016.
 */
@Controller
public class DefaultController {

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String helloWorld() {
        return "index";
    }
}
