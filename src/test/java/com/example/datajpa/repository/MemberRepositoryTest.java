package com.example.datajpa.repository;


import com.example.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;

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

}
