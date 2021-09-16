package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.integrated.model.MatchIntegratedDto;
import kr.co.player.api.domain.match.model.common.MatchType;
import kr.co.player.api.domain.match.model.common.MatchUserRole;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted=0")
@Table(name = "tbl_match_user")
@AttributeOverride(name = "id", column = @Column(name = "match_user_id"))
public class MatchUserEntity extends BaseEntity {

    @ManyToOne(targetEntity = MatchEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private MatchEntity matchEntity;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @Enumerated(EnumType.STRING)
    private MatchUserRole matchUserRole;


    public MatchIntegratedDto.MATCH_USER toDomain() {
        return MatchIntegratedDto.MATCH_USER.builder()
                .identity(this.userEntity.getIdentity())
                .name(this.userEntity.getName())
                .matchType(this.matchType)
                .matchUserRole(this.matchUserRole)
                .build();
    }

}
