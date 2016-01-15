package talkboard.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
