package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.model.common.*;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "birth")
    private String birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @Column(name = "likeCnt")
    private int likeCnt;

    @Column(name = "rudeCnt")
    private int rudeCnt;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Embedded
    private Position position;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "refresh_token", length = 600)
    private String refreshToken;

    @Builder
    public UserEntity(String identity, String password, String name, UserPhone userPhone, String birth, Gender gender, UserRole role) {
        this.identity = identity;
        this.password = password;
        this.name = name;
        this.userPhone = userPhone;
        this.birth = birth;
        this.gender = gender;
        this.role = role;
    }

    public void update(UserDto.UPDATE update) {
        this.name = update.getName();
        this.address = new Address(update.getCity(), update.getDistrict());
        this.position = new Position(
                PositionType.of(update.getPositionType()),
                PositionDetailsType.of(update.getMainPosition()),
                PositionDetailsType.of(update.getSubPosition())
        );
        this.userPhone = new UserPhone(update.getUserPhone());
        this.height = update.getHeight();
        this.weight = update.getWeight();
    }

    public void updatePassword(String password) {
        this.password = password;
    }
    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserDto.READ toDomain(List<String> imageUrlList) {
        return UserDto.READ.builder()
                .identity(this.identity)
                .name(this.name)
                .birth(this.birth)
                .gender(this.gender)
                .likeCnt(this.likeCnt)
                .rudeCnt(this.rudeCnt)
                .position(this.position)
                .userPhone(this.userPhone)
                .address(this.address)
                .height(this.height)
                .weight(this.weight)
                .imageUrl(imageUrlList)
                .build();
    }
}
