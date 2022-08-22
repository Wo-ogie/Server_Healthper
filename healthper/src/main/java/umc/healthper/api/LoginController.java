package umc.healthper.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import umc.healthper.global.argumentresolver.Login;
import umc.healthper.global.login.SessionConst;
import umc.healthper.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/home")
    @ResponseBody
    public String home(@Login Long userId) {
        if(userId == null)
            return "로그인 해주세요.";
        return "로그인 완료";
    }


    @Operation(summary = "Login",
            description = "Login")
    @GetMapping("/login")
    public String login(@RequestParam(defaultValue = "-1", name="kakaoId") Long kakaoKey,
                        @RequestParam(defaultValue = "/record/calender") String redirectURL, HttpServletRequest request) throws JsonProcessingException {

        if(kakaoKey == null){
            return "redirect:/home";
        }
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        Long userId = memberService.findByKakaoKey(kakaoKey).getId();
        session.setAttribute(SessionConst.LOGIN_MEMBER, userId);

        LocalDate now = LocalDate.now();
        return "redirect:"+redirectURL+"?year="+now.getYear()+"&month="+now.getMonthValue();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/home";
    }
}
