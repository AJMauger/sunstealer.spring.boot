package ajm.net.sunstealer.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class SunstealerBootApplication {

	public static void main(String[] args) {

		var logger = (Logger) LoggerFactory.getLogger(SunstealerBootApplication.class);
        logger.info("SunstealerBootApplication.main().");

		SpringApplication.run(SunstealerBootApplication.class, args);
	}
}
