package SpringSecurity.security.controller;


import SpringSecurity.security.repository.UserRepository;
import SpringSecurity.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager(){
        return "manager";
    }

    // 얘는 만들어봤자 SpringSecurity 가 해당주소를 낚아채서 로그인 화면 띄워버림 -> SecurityConfig 파일 생성해주면 안낚아챔
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");

        // 기존 패스워드(ex. 1234) 를 인코딩해주는 과정
        String rawPassword = user.getPassword(); // 기존 패스워드를
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 인코딩해주고
        user.setPassword(encPassword); // 인코딩준걸 새로 저장
        ////////////

        userRepository.save(user); // 회원 가입 잘되지만,비밀번호: 1234로 회원가입 하면 시큐리티로 로그인 할 수 없음. 이유는 패스워드 암호화가 안되었기 때문
        return "redirect:/loginForm";
    }

    // 물론 SecurityConfig 에서 andMatcher()로 글로벌하게 설정할 수 있지만 @Secured 써서 단일 컨트롤러 매서드 설정도 가능하다.
    @Secured("ROLE_ADMIN") // ROLE_ADMIN 의 권한을 가진 계정만 /info 에 접속 가능
    @GetMapping("/info")
    @ResponseBody
    public String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    @ResponseBody
    public String data(){
        return "데이터정보";
    }
}