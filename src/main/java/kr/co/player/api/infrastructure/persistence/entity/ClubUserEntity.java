package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.club.model.common.ClubUserRole;
import kr.co.player.api.domain.user.model.common.PositionType;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tbl_club_user")
@Where(clause = "deleted=0")
@AttributeOverride(name = "id", column = @Column(name = "club_user_id"))
@Getter
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
}
