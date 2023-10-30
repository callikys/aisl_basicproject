package basicproject.basicproject.controller;

import basicproject.basicproject.domain.member.Member;
import basicproject.basicproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {
    private final MemberRepository memberRepository;

    @Autowired
    public HomeController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/")
    public String main(@CookieValue(name = "name", required = false) String name, Model model) {

        if(name == null) {
            return "main";
        }

        Optional<Member> loginUser = memberRepository.findByName(name);
        if(!loginUser.isPresent()) {
            return "main";
        }

        model.addAttribute("member", loginUser.get());
        return "loginMain";
    }

    @GetMapping("/loginMain")
    public String loginmain(@CookieValue(name = "name", required = false) String name, Model model) {

        if(name == null) {
            return "loginMain";
        }

        Optional<Member> loginUser = memberRepository.findByName(name);
        if(!loginUser.isPresent()) {
            return "loginMain";
        }

        model.addAttribute("member", loginUser.get());
        return "loginMain";
    }
}
