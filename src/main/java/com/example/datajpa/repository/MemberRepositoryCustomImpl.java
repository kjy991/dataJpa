package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {

        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
