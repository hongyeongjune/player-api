package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.match.model.common.MatchLevel;
import kr.co.player.api.domain.match.model.common.MatchStatus;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted=0")
@Table(name = "tbl_match")
@AttributeOverride(name = "id", column = @Column(name = "match_id"))
public class MatchEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "player_count")
    private int playerCount;

    @Embedded
    private Address address;

    @ManyToOne(targetEntity = ClubEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "home_club_id")
    private ClubEntity homeClubEntity;

    @ManyToOne(targetEntity = ClubEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "away_club_id")
    private ClubEntity awayClubEntity;

    @Column(name = "home_score")
    private int homeScore;

    @Column(name = "away_score")
    private int awayScore;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "home_leader_id")
    private UserEntity homeUserEntity;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "away_leader_id")
    private UserEntity awayUserEntity;
}
