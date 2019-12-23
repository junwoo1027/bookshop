package com.junwoo.bookshop.domain.member;

import com.junwoo.bookshop.domain.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long Id;

    private String name;

    private String email;

    private String picture;

    private Role role;

    @Embedded
    private Address address;

    @Builder
    public Member(Long id, String name, String email, String picture, Role role, Address address) {
        Id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.address = address;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }
}
