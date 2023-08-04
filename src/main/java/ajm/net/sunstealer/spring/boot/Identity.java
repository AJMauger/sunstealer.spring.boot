package ajm.net.sunstealer.spring.boot;

import java.util.*;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.web.client.RestTemplate;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class Identity {

    private Logger logger = (Logger) LoggerFactory.getLogger(Identity.class);
    private static  Identity identity;
    
    public String accessToken;
    public String authorizationCode;
    public String clientId = "sunstealer.code";
    public String refreshToken;

    public AuthorizationGrantType authorizationGrantType = AuthorizationGrantType.AUTHORIZATION_CODE;
    public String registrationId = "sunstealer";
    public String[] scopes = { "offline_access", "openid", "profile" };
    public String userNameAttributeName = IdTokenClaimNames.SUB;

    public String authorizationUri = "https://ajmwin11-01.ajm.net:9001/connect/authorize";
    public String jwkSetUri = "https://ajmwin11-01.ajm.net:9001/.well-known/openid-configuration/jwks";
    public String redirectUriCode = "https://ajmfdr37-01.ajm.net:8443/oidc";
    public String redirectUriToken = "https://ajmfdr37-01.ajm.net:8443";
    public String tokenUri = "https://ajmwin11-01.ajm.net:9001/connect/token";
    public String userInfoUri = "https://ajmwin11-01.ajm.net:9001/connect/userinfo";

    static Identity instance() {
        if (identity == null) {
            identity = new Identity();
        }
        return identity;
    }
}
