package kr.co.player.api.infrastructure.persistence.entity;

import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_club")
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "club_id"))
@Where(clause = "deleted=0")
@Getter
public class ClubEntity extends BaseEntity {

    @Column(name = "club_name", nullable = false)
    private String clubName;

    @Embedded
    private Address address;


}
