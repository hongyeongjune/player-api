package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.user.model.common.*;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "tbl_user")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Where(clause = "deleted=0")
public class UserEntity extends BaseEntity {

    @Column(unique = true, name = "identity", nullable = false, length = 100)
    private String identity;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "name")
    private String name;

    @Embedded
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "user_phone"))
    })
    private UserPhone userPhone;

    @Column(name = "likeCnt")
    private int likeCnt;

    @Column(name = "rudeCnt")
    private int rudeCnt;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Embedded
    private Position position;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "refresh_token", length = 600)
    private String refreshToken;
}
