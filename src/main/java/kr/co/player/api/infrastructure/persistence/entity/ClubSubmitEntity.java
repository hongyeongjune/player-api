package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tbl_club_submit")
@Where(clause = "deleted=0")
@AttributeOverride(name = "id", column = @Column(name = "club_submit_id"))
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubSubmitEntity extends BaseEntity {

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;

    @ManyToOne(targetEntity = ClubUserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
