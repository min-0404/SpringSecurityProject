package SpringSecurity.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 됨
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    // 패스워드 인코딩 해주는 함수
    @Bean // 빈으로 등록 -> 해당 매서드에서 리턴되는 오브젝트를 IoC에 등록해줌
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder(); // 이 객체를 사용하면 입력받은 문자열을 인코딩해줌 -> 컨트롤러에서 사용해보자
    }

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
                .loginPage("/loginForm") // /user, /manager, /admin 에 접속하면 무조건 /login 페이지로 가도록 설정
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌 -> 따라서 컨트롤러에서 /login 주소 담당 매서드를 만들필요가 없음
                .defaultSuccessUrl("/"); // 로그인 성공하면 해당 주소로 이동시켜줌

    }


}
