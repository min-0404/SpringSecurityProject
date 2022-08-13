package SpringSecurity.security.controller;


import SpringSecurity.security.repository.UserRepository;
import SpringSecurity.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired(required = true)
    private UserRepository userRepository;

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
    @ResponseBody
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        //userRepository.save(user); // 회원 가입 잘되지만,비밀번호: 1234 -> 시큐리티로 로그인 할 수 없음. 이유는 패스워드 암호화가 안되었기 때문
        return "join";
    }
}