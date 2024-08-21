package com.example.demo.model.dto;

import com.example.demo.model.dto.entity.Member;

// DB -> 백 -> 프론트
// DB에서 들어가있는 컬럼들 중에 필요없는 것들은 제외시키기 위함
public class MemberInfo {
    private long id;
    private String name;

    public MemberInfo(Member member) {
        this.id = member.getMemberId();
        this.name = member.getMemberName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
