package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.user.model.common.*;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private UserAddress userAddress;

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

    @Builder
    public UserEntity(String identity, String password, String name, UserAddress userAddress, UserPhone userPhone, int likeCnt, int rudeCnt, String imageUrl, UserRole role, Position position, String fcmToken, String refreshToken) {
        this.identity = identity;
        this.password = password;
        this.name = name;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.likeCnt = likeCnt;
        this.rudeCnt = rudeCnt;
        this.imageUrl = imageUrl;
        this.role = role;
        this.position = position;
        this.fcmToken = fcmToken;
        this.refreshToken = refreshToken;
    }
}
