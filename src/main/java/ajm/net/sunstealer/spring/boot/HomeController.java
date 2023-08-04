package ajm.net.sunstealer.spring.boot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

@Controller
public class HomeController {
    private Logger logger = (Logger) LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home() {
        logger.info("HomeController.home().");
        return "home";
    }
}