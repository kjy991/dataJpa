package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void testMember() {
        Member member = Member.builder()
                .username("김준엽")
                .build();
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void basicCRUD() {
        Member memberA = Member.builder()
                .username("memberA")
                .build();
        Member memberB = Member.builder()
                .username("memberB")
                .build();

        memberJpaRepository.save(memberA);
        memberJpaRepository.save(memberB);

        Member findMemberA = memberJpaRepository.findById(memberA.getId()).get();
        Member findMemberB = memberJpaRepository.findById(memberB.getId()).get();
        assertThat(findMemberA).isEqualTo(memberA);
        assertThat(findMemberB).isEqualTo(memberB);

        findMemberA.setUsername("change MemberA");


        List<Member> memberList = memberJpaRepository.findAll();
        assertThat(memberList.size()).isEqualTo(2);

        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(memberA);
        memberJpaRepository.delete(memberB);
        long deleteCount = memberJpaRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member memberA = Member.builder()
                .username("A")
                .age(10)
                .build();

        Member memberB = Member.builder()
                .username("A")
                .age(20)
                .build();

        memberJpaRepository.save(memberA);
        memberJpaRepository.save(memberB);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("A", 15);
        for (Member member : result) {
            System.out.println("member = " + member);
        }

        assertThat(result.get(0).getUsername()).isEqualTo("A");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void paging() {
        //given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));
        memberJpaRepository.save(new Member("member6", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);


        //then
        assertThat(members.size()).isEqualTo(3L);
        assertThat(totalCount).isEqualTo(6L);
    }

    @Test
    public void bulkUpdate(){
        // given
        memberJpaRepository.save(new Member("member1",10));
        memberJpaRepository.save(new Member("member2",11));
        memberJpaRepository.save(new Member("member3",12));
        memberJpaRepository.save(new Member("member4",13));
        memberJpaRepository.save(new Member("member5",14));
        memberJpaRepository.save(new Member("member6",15));
        memberJpaRepository.save(new Member("member7",16));

        // when
        int resultCount = memberJpaRepository.bulkAgePlus(13);

        assertThat(resultCount).isEqualTo(4);


    }

}
















