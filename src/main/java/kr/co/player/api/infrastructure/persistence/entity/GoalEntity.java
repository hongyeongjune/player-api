package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.match.model.common.MatchType;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "tbl_goal")
@Where(clause = "deleted=0")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "goal_id"))
public class GoalEntity extends BaseEntity {

    @Column(name = "time")
    private int time;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(targetEntity = MatchEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private MatchEntity matchEntity;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "assist_user_id")
    private UserEntity assistUserEntity;
}
