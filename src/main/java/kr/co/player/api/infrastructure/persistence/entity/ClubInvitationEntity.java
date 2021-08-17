package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.invitation.model.ClubInvitationDto;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tbl_club_invitation")
@Where(clause = "deleted=0")
@AttributeOverride(name = "id", column = @Column(name = "club_invitation_id"))
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubInvitationEntity extends BaseEntity {

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;

    @ManyToOne(targetEntity = ClubUserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_user_id")
    private ClubUserEntity clubUserEntity;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public void updateStatus(JoinStatus joinStatus) {
        this.joinStatus = joinStatus;
    }

    public ClubIntegratedDto.READ_INVITATION toIntegratedDomain() {
        return ClubIntegratedDto.READ_INVITATION.builder()
                .identity(this.userEntity.getIdentity())
                .name(this.userEntity.getName())
                .message(this.message)
                .joinStatus(this.joinStatus)
                .build();
    }

    public ClubInvitationDto.READ toDomain() {
        return ClubInvitationDto.READ.builder()
                .clubName(this.clubUserEntity.getClubEntity().getClubName())
                .identity(this.clubUserEntity.getUserEntity().getIdentity())
                .name(this.clubUserEntity.getUserEntity().getName())
                .message(this.message)
                .joinStatus(this.joinStatus)
                .build();
    }
}
