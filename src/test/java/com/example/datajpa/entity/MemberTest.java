package com.example.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void testEntity() {
        Team teamA = Team.builder()
                .name("TeamA")
                .build();
        Team teamB = Team.builder()
                .name("TeamB")
                .build();

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = Member.builder()
                .username("엽킹")
                .age(11)
                .team(teamB)
                .build();
        Member member2 = Member.builder()
                .username("킹엽")
                .age(22)
                .team(teamA)
                .build();

        Member member3 = Member.builder()
                .username("엽킹3")
                .age(33)
                .team(teamB)
                .build();
        Member member4 = Member.builder()
                .username("킹엽4")
                .age(44)
                .team(teamA)
                .build();

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();

        List<Member> resultList = em.createQuery("select m from Member m ", Member.class)
                .getResultList();
        for (Member member : resultList) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }

    }

}