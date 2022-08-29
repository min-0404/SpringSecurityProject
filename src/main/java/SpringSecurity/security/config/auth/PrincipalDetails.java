package SpringSecurity.security.config.auth;
import SpringSecurity.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


// <스프링 시큐리티 작동 원리>
// 클라이언트에게 /login 요청 오면 시큐리티가 요청을 낚아채서 로그인 진행시켜줌
// 로그인 완료되면, Security Session 이라는 공간을 만들어줌(=스프링 시큐리티가 제공하는 세션 저장 공간)
// 이것을 "SecurityContextHolder' 라고 함
// 이 스프링 세션에는 Authentication 타입의 오브젝트만 들어갈 수 있음
// 이 Authentication 안에는 User 정보가 있어야함
// 이 User 정보의 오브젝트 타입이 UserDetails 타입의 객체임
// 정리: SecuritySession(=SecurityContextHolder) -> Authentication -> UserDetails
// 결국 이러한 형태가 되어야함: SecuritySession(Authentication(UserDetails))

public class PrincipalDetails implements UserDetails { // PrincipalDetails 는 그냥 UserDetails 타입의 구현체

    private SpringSecurity.security.model.User user;

    // 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }


    // 해당 유저의 권한(Role)을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>(); // 그냥 Authentication 만 리턴해버리면 안되므로 collection 활용하자

        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    // password 리턴
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // username 리턴
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료x
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠김x
    }

    // 계정 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 만료x
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {

        // 1년동안 회원이 로그인을 안하면 휴면 계정으로 설정하기
        // 현재시간 - 로긴시간 = 1년을 초과하면 return false;

        return true; // 활성화 o
   }
}
