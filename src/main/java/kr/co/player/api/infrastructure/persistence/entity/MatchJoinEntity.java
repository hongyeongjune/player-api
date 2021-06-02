package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.match.model.common.JoinType;
import kr.co.player.api.domain.match.model.common.MatchType;
import kr.co.player.api.domain.match.model.common.MatchUserRole;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tbl_match_invitation")
@Where(clause = "deleted=0")
@AttributeOverride(name = "id", column = @Column(name = "match_invitation_id"))
public class MatchJoinEntity extends BaseEntity {

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @Enumerated(EnumType.STRING)
    private MatchUserRole matchUserRole;

    @Enumerated(EnumType.STRING)
    private JoinType joinType;

    @ManyToOne(targetEntity = MatchEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private MatchEntity matchEntity;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
