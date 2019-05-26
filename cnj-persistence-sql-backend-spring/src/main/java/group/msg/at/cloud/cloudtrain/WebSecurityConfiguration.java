package group.msg.at.cloud.cloudtrain;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.client.RestTemplate;

/**
 * Keycloak-specific web security configuration.
 * <p>
 * All REST endpoints except the actuator REST endpoints are protected by role {@code CLOUDTRAIN_USER}
 * which is assigned to all authenticated users of this application when logging in on Keycloak.
 * </p>
 * <p>
 * More specific endpoint URI to role mappings can be added to {@link #configure(HttpSecurity)}.
 * </p>
 * <p>All actual OpenID Connect configuration is provided through {@code application.properties}:</p>
 * <ul>
 * <li>keycloak.realm: defines the Keycloak realm to use for this application</li>
 * <li>keycloak.auth-server-url: defines the Keycloak server URL to use (like https://login.at.automotive.msg.team/auth)</li>
 * <li>keycloak.resource: client ID to be sent ot Keycloak when validating tokens</li>
 * <li>keycloak.credentials.secret: client secret to be sent to Keycloak when validating tokens</li>
 * </ul>
 * <p>
 * Before this application may use Keycloak as an OpenID Connect provider, this application must be registered as
 * an OpenID Connect client with Keycloak.
 * </p>
 *
 * @author michael.theis@msg.group
 * @version 1.0
 */
@KeycloakConfiguration
public class WebSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {
    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     * <p>
     * By adding a {@code SimpleAuthorityMapper} to the original {@code KeycloakAuthenticationProvider} we ensure
     * </p>
     * <ul>
     * <li>that all role names are registered using a {@code ROLE_} prefix (which is spring security's default)</li>
     * <li>that all role names are registered using uppercase letters</li>
     * </ul>
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider authenticationProvider = keycloakAuthenticationProvider();
        SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
        authoritiesMapper.setConvertToUpperCase(true);
        authenticationProvider.setGrantedAuthoritiesMapper(authoritiesMapper);
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * Defines the session authentication strategy as {@code NullAuthenticatedSessionStrategy} since we are
     * providing a bearer-token only application (i.e. just REST endpoints without any web pages).
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    /**
     * Defines the security rules applied to different request URIs.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable() // <== essential to switch off CSRF checking!
                .authorizeRequests()
                .antMatchers("/api/v1/tasks").hasRole("CLOUDTRAIN_USER")
                .anyRequest().permitAll();
    }

    /**
     * Provide a custom {@code KeycloakConfigResolver} since we do not want to use JSON file based configuration but
     * Spring Boot AutoConfig based configuration for cloud compatibility (configuration through envvars).
     */
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * Provides a custom {@code RestTemplate} for requests to downstream services that handles bearer token
     * authentication.
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestTemplate keycloakRestTemplate(KeycloakClientRequestFactory clientRequestFactory) {
        return new KeycloakRestTemplate(clientRequestFactory);
    }
}
