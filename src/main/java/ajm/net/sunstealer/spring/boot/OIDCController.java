package ajm.net.sunstealer.spring.boot;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

@RestController
public class OIDCController {
    private Logger logger = (Logger) LoggerFactory.getLogger(OIDCController.class);

    @GetMapping("/oidc")
    public void oidc(@RequestParam(name="code", required=false, defaultValue="error") String code, Model model) {
        logger.info(String.format("OIDCController.oidc() code: %s.", code));
        Identity.instance().authorizationCode = code;

        try {
            var body = "client_id=sunstealer.code&client_secret=secret&code=" + code 
                + "&grant_type=authorization_code&redirect_uri=" + Identity.instance().redirectUriToken;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(Identity.instance().tokenUri))
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info(String.format("response: %s.", response));
        } catch(Exception e) {
            logger.error(String.format("Exception: %s.", e.toString()));
        }
    }
}