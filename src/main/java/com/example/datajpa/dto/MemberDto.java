package com.example.datajpa.dto;

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
}
