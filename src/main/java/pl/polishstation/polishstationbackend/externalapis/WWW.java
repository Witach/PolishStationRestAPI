package pl.polishstation.polishstationbackend.externalapis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WWW {
    @RequestMapping("/www")
    String www(){
        return "template";
    }
}
