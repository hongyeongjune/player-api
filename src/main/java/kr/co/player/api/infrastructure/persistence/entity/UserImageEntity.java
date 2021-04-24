package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_image")
@Entity
@AttributeOverride(name = "id", column = @Column(name = "teaImage_id"))
@Where(clause = "deleted=0")
@Builder
@AllArgsConstructor
public class UserImageEntity extends BaseEntity {

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "user_id")
    private Long userEntity;
}
