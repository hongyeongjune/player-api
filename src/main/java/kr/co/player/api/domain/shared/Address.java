package kr.co.player.api.domain.shared;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Address {

    @ApiModelProperty(example = "경기도")
    @Column(name = "city")
    private String city;

    @ApiModelProperty(example = "구리시")
    @Column(name = "district")
    private String district;

    @Builder
    public Address(String city, String district) {
        this.city = city;
        this.district = district;
    }
}
