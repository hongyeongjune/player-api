package kr.co.player.api.infrastructure.persistence;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

// 객체 입장에서 공통 매핑 정보가 필요할 때 사용
@MappedSuperclass
// 공통적으로 가지고 있는 필드나 컬럼들을 시간에 대해서 자동으로 값을 넣어주는 기능
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of="id", callSuper = false)
@Getter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "deleted", nullable = false, columnDefinition = "BIT default 0")
    protected Boolean deleted = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createAt = null;

    @LastModifiedDate
    @Column(name = "last_modified_at", nullable = false)
    protected LocalDateTime lastModifiedAt = null;

    public void deleted() {
        deleted = true;
    }

}
