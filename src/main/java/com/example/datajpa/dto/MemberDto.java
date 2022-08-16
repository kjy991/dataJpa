package com.example.datajpa.dto;

import com.example.datajpa.entity.Member;
import lombok.Data;

@Data
public class MemberDto {

    private Long id;
    private String username;
    private String team;

    public MemberDto(Long id, String username, String team) {
        this.id = id;
        this.username = username;
        this.team = team;
    }

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();

    }
}
