package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.match.model.common.MatchPlayerType;
import kr.co.player.api.domain.match.model.common.MatchType;
import kr.co.player.api.domain.user.model.common.PositionDetailsType;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "tbl_squad")
@Where(clause = "deleted=0")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "squad_id"))
public class SquadEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private PositionDetailsType positionDetailsType;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @Enumerated(EnumType.STRING)
    private MatchPlayerType matchPlayerType;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(targetEntity = MatchEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private MatchEntity matchEntity;
}
