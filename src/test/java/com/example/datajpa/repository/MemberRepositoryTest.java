package com.example.datajpa.repository;


import com.example.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testMember() {
        Member member = Member.builder()
                .username("김준엽킹")
                .build();

        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId()).get();

        Assertions.assertThat(saveMember.getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(saveMember.getUsername()).isEqualTo(findMember.getUsername());
        Assertions.assertThat(saveMember).isEqualTo(findMember);

    }


    @Test
    void basicCRUD(){
        Member memberA = Member.builder()
                .username("memberA")
                .build();
        Member memberB = Member.builder()
                .username("memberB")
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Member findMemberA = memberRepository.findById(memberA.getId()).get();
        Member findMemberB = memberRepository.findById(memberB.getId()).get();
        assertThat(findMemberA).isEqualTo(memberA);
        assertThat(findMemberB).isEqualTo(memberB);

        findMemberA.setUsername("change MemberA");


        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(memberA);
        memberRepository.delete(memberB);
        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);

    }


    @Test
    public void findByUsernameAndAgeGreaterThan(){
        Member memberA = Member.builder()
                .username("A")
                .age(10)
                .build();

        Member memberB = Member.builder()
                .username("A")
                .age(20)
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("A", 15);
        for (Member member : result) {
            System.out.println("member = " + member);
        }

        assertThat(result.get(0).getUsername()).isEqualTo("A");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void findHelloBy(){
        List<Member> helloBy = memberRepository.findHelloBy();
        for (Member member : helloBy) {
            System.out.println("member = " + member);
        }







    }


}
