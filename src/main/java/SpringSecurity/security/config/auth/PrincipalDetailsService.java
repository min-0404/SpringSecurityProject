package SpringSecurity.security.config.auth;

import SpringSecurity.security.model.User;
import SpringSecurity.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정(Security Config)에서 loginProcessingUrl("/login"); 설정해놓았음
// 따라서 클라이언트가 /login 접속해서, 스프링에 요청이 오면 UserDetailsService 타입으로 IoC 되어있는 PrincipalDetailsService 객체를 찾고,
// PrincipalDetailsService 안의 loadUserByUsername 함수를 실행시킴
// 이 함수는 html Form 에서 파라미터로 넘어온 username 을 받아들인 다음 UserDetail 타입의 객체를 생성해서 반환해준다.

@Service // IoC 컨테이너에 등록해놓음
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    // 다시 한번 정리: Security Session 안에 = Authentication 타입 안에 = UserDetails 타입이 들어와야 함
    // 결국 이러한 형태가 되어야 함: SecuritySession(Authentication(UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username:" + username);

        User userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            return new PrincipalDetails(userEntity); // UserDetails 타입의 객체를 반환
        }
        return null;
    }
}
