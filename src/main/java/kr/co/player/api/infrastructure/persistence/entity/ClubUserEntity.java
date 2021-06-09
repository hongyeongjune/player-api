package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.club.model.ClubUserDto;
import kr.co.player.api.domain.club.model.common.ClubUserRole;
import kr.co.player.api.domain.user.model.common.PositionType;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tbl_club_user")
@Where(clause = "deleted=0")
@AttributeOverride(name = "id", column = @Column(name = "club_user_id"))
@Getter
@Builder
public class ClubUserEntity extends BaseEntity {

    @ManyToOne(targetEntity = ClubEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "uniform_number")
    private int uniformNumber;

    @Column(name = "club_position_type")
    @Enumerated(value = EnumType.STRING)
    private PositionType clubPositionType;

    @Enumerated(value = EnumType.STRING)
    private ClubUserRole clubUserRole;

    public ClubUserDto.READ_MY_CLUB toDomain() {
        return ClubUserDto.READ_MY_CLUB.builder()
                .name(this.clubEntity.getClubName())
                .description(this.clubEntity.getDescription())
                .address(this.clubEntity.getAddress())
                .rating(this.clubEntity.getRating())
                .build();
    }
}
