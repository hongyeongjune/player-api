package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "tbl_club_image")
@Entity
@AttributeOverride(name = "id", column = @Column(name = "club_image_id"))
@Where(clause = "deleted=0")
@AllArgsConstructor
public class ClubImageEntity extends BaseEntity {
    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(targetEntity = ClubEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;
}
