package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.club.model.ClubDto;
import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_club")
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "club_id"))
@Where(clause = "deleted=0")
@Getter
@Builder
public class ClubEntity extends BaseEntity {

    @Column(name = "club_name", unique = true, nullable = false)
    private String clubName;

    @Embedded
    private Address address;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private double rating;

    public ClubIntegratedDto.READ toDomain(ClubUserEntity clubUserEntity, Long count) {
        return ClubIntegratedDto.READ.builder()
                .name(this.clubName)
                .address(this.address)
                .description(this.description)
                .rating(this.rating)
                .userCount(count)
                .identity(clubUserEntity.getUserEntity().getIdentity())
                .userName(clubUserEntity.getUserEntity().getName())
                .build();
    }

    public void update(ClubDto.UPDATE update) {
        this.address = new Address(update.getCity(), update.getDistrict());
        this.description = update.getDescription();
    }

    public void update(String clubName) {
        this.clubName = clubName;
    }
}
