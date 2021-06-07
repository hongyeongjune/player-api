package kr.co.player.api.domain.user.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Position {

    @ApiModelProperty("FW or MF or DF of GK")
    @Enumerated(EnumType.STRING)
    private PositionType positionType;

    @ApiModelProperty("LW or ST or CF or RW or LM or CM or CDM or RM or LWB or LB or CB or RB or RWB or GK")
    @Enumerated(EnumType.STRING)
    private PositionDetailsType mainPosition;

    @ApiModelProperty("LW or ST or CF or RW or LM or CM or CDM or RM or LWB or LB or CB or RB or RWB or GK")
    @Enumerated(EnumType.STRING)
    private PositionDetailsType subPosition;

    @Builder
    public Position(PositionType positionType, PositionDetailsType mainPosition, PositionDetailsType subPosition) {
        this.positionType = positionType;
        this.mainPosition = mainPosition;
        this.subPosition = subPosition;
    }
}
