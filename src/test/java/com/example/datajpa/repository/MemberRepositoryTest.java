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
                .userName("김준엽킹")
                .build();

        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId()).get();

        Assertions.assertThat(saveMember.getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(saveMember.getUserName()).isEqualTo(findMember.getUserName());
        Assertions.assertThat(saveMember).isEqualTo(findMember);

    }


    @Test
    void basicCRUD(){
        Member memberA = Member.builder()
                .userName("memberA")
                .build();
        Member memberB = Member.builder()
                .userName("memberB")
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Member findMemberA = memberRepository.findById(memberA.getId()).get();
        Member findMemberB = memberRepository.findById(memberB.getId()).get();
        assertThat(findMemberA).isEqualTo(memberA);
        assertThat(findMemberB).isEqualTo(memberB);

        findMemberA.setUserName("change MemberA");


        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(memberA);
        memberRepository.delete(memberB);
        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);

    }


}
