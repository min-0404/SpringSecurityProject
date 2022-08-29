package SpringSecurity.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화시킴 + preAuthorize, postAuthorize 두개의 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    // 패스워드 인코딩 해주는 함수
    @Bean // 빈으로 등록 -> 해당 매서드에서 리턴되는 오브젝트를 IoC에 등록해줌
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder(); // 이 객체를 사용하면 입력받은 문자열을 인코딩해줌 -> 컨트롤러에서 사용해보자
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable(); // csrf 비활성화
        http.authorizeRequests() // 각 주소별로 접속하기 위해 필요한 "접근 권한" 설정
                .antMatchers("/user/**").authenticated()// 인증만 되면 들어갈 수 있는 주소라는 걸 설정
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")// ROLE_ADMIN 이나 ROLE_MANAGER 여야만 들어갈 수 있는 주소라는 것을 설정
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")// ROLE_ADMIN 이어야만 들어갈 수 있는 주소라는 것을 설정
                .anyRequest().permitAll() // 위의 3가지 주소가 아니면 인증 없이 누구나 들어갈 수 있다고 설정
                .and()
                .formLogin()
                .loginPage("/loginForm") // /user, /manager, /admin 에 접속하면 무조건 /loginForm 페이지로 가도록 설정
                //                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌 -> 따라서 /login 담당하는 컨트롤러 매서드를 만들 필요가 없음
                //                .defaultSuccessUrl("/") // 로그인 성공하면 내가 처음에 원했던 주소로 이동시켜줌.
                //                                        // ex) /user 접속하면 일단 /loginForm 페이지로 이동되고, 로그인 정보 입력하면 /login 컨트롤러 실행되고(물론 시큐리티가 자동으로 구현해둠)
                                        // 로그인 성공하면 기존에 내가 접속하려고 했던 /user를 실행시켜줌
                .and()
                .oauth2Login() // oauth 설정
                .loginPage("/loginForm");
                // 구글 로그인이 완료된 뒤 후처리가 필요함.
                // 1. 코드받기(인증) 2. 엑세스토큰 받기(권한) 3. 사용자 프로필 정보를 가져와서 4. 그 정보를 토대로 회원가입 자동으로 진행

    }


}
