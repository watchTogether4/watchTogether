package com.watchtogether.server.ott.domain.dto;

import com.watchtogether.server.ott.domain.entity.Ott;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OttDto {

    private Long id;
    private String name;
    private int commissionLeader;
    private int commissionMember;
    private Long fee;
    private String imagePath;

    public static OttDto fromEntity(Ott ott) {
        return OttDto.builder()
            .id(ott.getId())
            .name(ott.getName())
            .commissionLeader(ott.getCommissionLeader())
            .commissionMember(ott.getCommissionMember())
            .fee(ott.getFee())
            .build();
    }
}
