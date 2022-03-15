package ir.maktab.firstspringboot.web.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Configuration
public class JwtSecretKey {

    private final JwtConfig jwtConfig;

    @Bean
    @Lazy
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(jwtConfig.getTokenSecret().getBytes());
    }
}

