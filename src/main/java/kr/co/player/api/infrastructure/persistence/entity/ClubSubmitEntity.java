package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.domain.submit.model.ClubSubmitDto;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tbl_club_submit")
@Where(clause = "deleted=0")
@AttributeOverride(name = "id", column = @Column(name = "club_submit_id"))
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ClubSubmitEntity extends BaseEntity {

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;

    @ManyToOne(targetEntity = ClubEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public void updateJoinStatus(JoinStatus joinStatus) {
        this.joinStatus = joinStatus;
    }

    public ClubSubmitDto.READ toDomain() {
        return ClubSubmitDto.READ.builder()
                .message(this.message)
                .clubName(this.clubEntity.getClubName())
                .joinStatus(this.joinStatus)
                .build();
    }

    public ClubIntegratedDto.READ_SUBMIT toIntegratedDomain() {
        return ClubIntegratedDto.READ_SUBMIT.builder()
                .identity(this.userEntity.getIdentity())
                .name(this.userEntity.getName())
                .message(this.message)
                .joinStatus(this.joinStatus)
                .build();
    }
}
