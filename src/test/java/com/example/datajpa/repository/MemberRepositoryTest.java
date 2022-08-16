package com.example.datajpa.repository;


import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

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
    void basicCRUD() {
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
    public void findByUsernameAndAgeGreaterThan() {
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
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findHelloBy() {
        List<Member> helloBy = memberRepository.findHelloBy();
        for (Member member : helloBy) {
            System.out.println("member = " + member);
        }


    }

    @Test
    public void findUser() {
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


        List<Member> resultList = memberRepository.findUser("A", 10);
        Member findMember = resultList.get(0);
        assertThat(findMember).isEqualTo(memberA);

    }

    @Test
    public void findUsernameList() {
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


        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDto() {
        Team teamA = Team.builder()
                .name("A_Team")
                .build();

        Team teamB = Team.builder()
                .name("B_Team")
                .build();

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member memberA = Member.builder()
                .username("AAA")
                .age(10)
                .team(teamA)
                .build();

        Member memberB = Member.builder()
                .username("BBB")
                .age(20)
                .team(teamB)
                .build();
        memberRepository.save(memberA);
        memberRepository.save(memberB);


        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }

    }


    @Test
    public void findByNames() {
        Member memberA = Member.builder()
                .username("A")
                .age(10)
                .build();

        Member memberB = Member.builder()
                .username("B")
                .age(20)
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);

//        memberRepository


        List<Member> byNames = memberRepository.findByNames(Arrays.asList("A", "B"));
        for (Member byName : byNames) {
            System.out.println("byName = " + byName);
        }
    }


    @Test
    public void returnType() {
        Member memberA = Member.builder()
                .username("A")
                .age(10)
                .build();

        Member memberB = Member.builder()
                .username("B")
                .age(20)
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);
//        Optional<Member> a = memberRepository.findOptionalByUsername("A");
//        assertThat(a).isnot
    }

    @Test
    public void paging() {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));


        int age = 10;

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
//        Slice<Member> page = memberRepository.findByAge(age, pageRequest);

        // dto로 꼭 변환 시켜서 api 줘야함 entity로 절대 주지 말것!!
        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));


        //then
        List<Member> content = page.getContent();

        assertThat(content.size()).isEqualTo(3L);
        assertThat(page.getTotalElements()).isEqualTo(6L);
        assertThat(page   .getTotalPages()).isEqualTo(2L);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

    }

    @Test
    public void bulkUpdate(){
        // given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",20));
        memberRepository.save(new Member("member3",30));
        memberRepository.save(new Member("member4",40));
        memberRepository.save(new Member("member5",50));
        memberRepository.save(new Member("member6",60));
        memberRepository.save(new Member("member7",70));

        // when
        int resultCount = memberRepository.bulkAgePlus(30);


        Member member5 = memberRepository.findByUsername("member5");
        System.out.println("=============================================================================================");
        System.out.println("member5 = " + member5);
        System.out.println("=============================================================================================");


        assertThat(resultCount).isEqualTo(5);


    }



}





































