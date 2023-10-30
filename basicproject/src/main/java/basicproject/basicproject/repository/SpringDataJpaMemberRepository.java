package basicproject.basicproject.repository;

import basicproject.basicproject.domain.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Primary
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, String>, MemberRepository{
    Optional<Member> findByName(String name);
}
