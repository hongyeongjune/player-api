package kr.co.player.api.infrastructure.security;

import kr.co.player.api.infrastructure.security.jwt.JwtAuthenticationFilter;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private HandlerExceptionResolver handlerExceptionResolver;
    private final AccessDeniedHandlerCustom accessDeniedHandlerCustom;
    private final AuthenticationEntryPointCustom authenticationEntryPointCustom;

    private static final String[] AUTH_ARR = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "favicon.ico"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(AUTH_ARR);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v1/api/**").permitAll()
                .and()
                //로그인은 했으나 권한이 없을 때
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerCustom)
                .and()
                // 로그인을 하지 않았을 때
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointCustom)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
