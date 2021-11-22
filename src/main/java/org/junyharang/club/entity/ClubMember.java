package org.junyharang.club.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junyharang.club.entity.base.BaseTimeEntity;
import org.junyharang.club.entity.base.ClubMemberRole;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity public class ClubMember extends BaseTimeEntity {

    @Id private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY) @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    public void addMemberRole(ClubMemberRole clubMemberRole) {
        roleSet.add(clubMemberRole);
    } // addMemberRole() 끝

} // class 끝
