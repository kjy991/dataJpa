package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m ")
    List<String> findUsernameList();

    @Query("select new com.example.datajpa.dto.MemberDto (m.id,m.username,t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username);

    Member findByUsername(String username);

    Optional<Member> findOptionalByUsername(String username);

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count (m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);


}
