package com.example.demo.model.repository;

import com.example.demo.model.dto.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.memberEmail = :email")
    public Optional<Member> findMemberByEmail(@Param("email") String email);
}
