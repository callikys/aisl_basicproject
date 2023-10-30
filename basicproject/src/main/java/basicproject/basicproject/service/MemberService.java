package basicproject.basicproject.service;

import basicproject.basicproject.domain.member.Member;
import basicproject.basicproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getName();
    }

    public Member login(String name, String pwd) {
        return memberRepository.findByName(name)
                .filter(m->m.getPwd().equals(pwd))
                .orElse(null);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getId()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(String name) {
        return memberRepository.findByName(name);
    }
}
