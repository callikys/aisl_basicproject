package basicproject.basicproject.controller;

import basicproject.basicproject.domain.member.Member;
import basicproject.basicproject.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private HttpServletResponse response;

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @GetMapping(value = "/members/list")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/list";
    }

    @GetMapping(value = "/members/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "members/login";
    }

    @GetMapping(value = "/members/me")
    public String myList() {return "members/me";}

    @PostMapping(value = "/members/new")
    public String create(basicproject.basicproject.controller.MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setPwd(form.getPwd());
        member.setGrade(form.getGrade());

        memberService.join(member);
        return "redirect:/";
    }

    @PostMapping(value = "/members/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "members/login";
        }
        Member loginUser = memberService.login(form.getName(), form.getPwd());

        if(loginUser==null) {
            bindingResult.reject("loginFail", "이름 또는 비밀번호가 맞지 않습니다.");
            return "members/login";
        }

        Cookie idCookie = new Cookie("name", String.valueOf(loginUser.getId()));
        response.addCookie(idCookie);
        return "redirect:/loginMain";
    }

    @PostMapping("logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "name");
        return "redirect:/";
    }
    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
