package com.flipkart.apigateway.filter;


import com.flipkart.apigateway.exception.UnAuthorizedUserException;
import com.flipkart.apigateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Conf> {

    private RouteValidator validator;

    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Conf.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public String getJwtFromHeader(HttpHeaders headers) {
        String authorizationHeader =headers.getFirst(AUTHORIZATION);
        String token = null;
        if (null != authorizationHeader && authorizationHeader.startsWith(BEARER)) {
            token = authorizationHeader.substring(7);
        } else {
            log.error("missing authorization header");
            throw new UnAuthorizedUserException("missing authorization header");
        }
        return token;
    }

    public static class Conf {
    }

    @Override
    public GatewayFilter apply(Conf config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                String token = getJwtFromHeader(exchange.getRequest().getHeaders());
                if (token != null && !token.isEmpty()) {
                    try {
                        jwtUtil.validateToken(token);

                    } catch (Exception e) {
                        log.error("un authorized access to application");
                        throw new RuntimeException("Unauthorized access of protected resource, Invalid credentials");
                    }
                }
            }
            return chain.filter(exchange);
        });
    }
}
