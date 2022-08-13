package SpringSecurity.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 됨
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable(); // csrf 비활성화
        http.authorizeRequests() // 각 주소별로 접속하기 위해 필요한 접근 권한 설정
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 위의 3가지 주소가 아니면 인증 없이 누구나 들어갈 수 있다고 설정
                .and()
                .formLogin()
                .loginPage("/loginForm"); // /user, /manager, /admin 에 접속하면 무조건 /login 페이지로 가도록 설정
    }


}
