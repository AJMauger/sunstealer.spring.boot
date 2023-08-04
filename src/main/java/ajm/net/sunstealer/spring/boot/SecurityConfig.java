package ajm.net.sunstealer.spring.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.endpoint.NimbusJwtClientAuthenticationParametersConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private Logger logger = (Logger) LoggerFactory.getLogger(SecurityConfig.class);

  @Configuration
  public class OAuth2LoginConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
      requestCache.setMatchingRequestParameterName("continue");
      http
        .authorizeHttpRequests(authorize -> authorize
            // .csrf()
            // .disable()
            .requestMatchers("/oidc").permitAll()  // ajm: catch authorization code response with OIDCController
            .anyRequest().authenticated()
          )
          .oauth2Login(Customizer.withDefaults())
          // .oauth2ResourceServer(oauth2 -> oauth2
          // .jwt())
          .requestCache((cache) -> cache
            .requestCache(requestCache)
          );
      return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
      var clientRegistration = ClientRegistration.withRegistrationId(Identity.instance().registrationId)
        .clientId(Identity.instance().clientId)
        
        .authorizationUri(Identity.instance().authorizationUri)
        .jwkSetUri(Identity.instance().jwkSetUri)
        .redirectUri(Identity.instance().redirectUriCode /*"{baseUrl}/login/oauth2/code/{registrationId}"*/)
        .tokenUri(Identity.instance().tokenUri)
        .userInfoUri(Identity.instance().userInfoUri)

        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope(Identity.instance().scopes)
        .userNameAttributeName(IdTokenClaimNames.SUB)
        .build();

      return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    /* @Bean
    public WebClientReactiveAuthorizationCodeTokenResponseClient webClientReactiveAuthorizationCodeTokenResponseClient() {
        var tokenResponseClient = new WebClientReactiveAuthorizationCodeTokenResponseClient();
        return tokenResponseClient;
    }*/
  }
}