package SpringSecurity.security.config.auth;
import SpringSecurity.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시켜줌
// 로그인 진행이 완료가 되면, Security Session 이라는 공간을 만들어줌(= 스프링 시큐리티가 제공하는 세션 저장 공간)
// 이것을 "SecurityConetextHolder" 라고 함
// 이 스프링 세션에 들어갈 수 있는 오브젝트가 -> Authentication 타입의 객체임
// 이 Authentication 안에는 User 정보가 있어야 함
// 이 User 정보의 오브젝트 타입-> UserDetails 타입의 객체임

// 정리: SecuritySession -> Authentication -> UserDetails
// 결국 이러한 형태가 되어야함: SecuritySession(Authentication(UserDetails))
public class PrincipalDetails implements UserDetails { // 즉, PrincipalDetails 는 UserDetails 타입의 구현체

    private SpringSecurity.security.model.User user;

    // 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }


    // 해당 유저의 권한(Role)을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();

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
