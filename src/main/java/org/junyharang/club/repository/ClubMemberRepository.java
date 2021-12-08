package org.junyharang.club.repository;

import org.junyharang.club.entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    // 이용자의 Email과 Social로 추가된 회원 여부를 선택하여 동작하도록 설계
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial =:social and m.email =:email")
    Optional<ClubMember> findByEmail(@Param("email") String email, @Param("social") boolean social);

} // interface 끝
