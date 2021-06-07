package kr.co.player.api.domain.user.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class UserPhone {

    @ApiModelProperty(example = "010-XXXX-XXXX")
    @Column(name = "number")
    private String number;

    public UserPhone(String number) {
        this.number = number;
    }
}
