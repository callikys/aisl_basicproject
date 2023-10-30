package basicproject.basicproject.repository;

import basicproject.basicproject.domain.member.Member;

import java.util.List;
import java.util.Optional;
public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByName(String name);

    List<Member> findAll();
}
