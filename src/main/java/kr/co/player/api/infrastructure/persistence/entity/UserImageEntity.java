package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "tbl_user_image")
@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_image_id"))
@Where(clause = "deleted=0")
@Builder
@AllArgsConstructor
public class UserImageEntity extends BaseEntity {

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
