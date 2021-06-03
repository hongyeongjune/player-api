package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.player.api.infrastructure.persistence.entity.QUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.QUserImageEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;
import kr.co.player.api.infrastructure.persistence.repository.UserImageRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class UserImageRepositoryCustomImpl extends QuerydslRepositorySupport implements UserImageRepositoryCustom {

    private final JPAQueryFactory jpaQuery;
    private final QUserImageEntity userImage = QUserImageEntity.userImageEntity;
    private final QUserEntity user = QUserEntity.userEntity;

    public UserImageRepositoryCustomImpl(JPAQueryFactory jpaQuery) {
        super(UserImageEntity.class);
        this.jpaQuery = jpaQuery;
    }

    @Override
    public Optional<UserImageEntity> fetchFirstOrderByIdDesc() {
        return Optional.ofNullable(setFetchJoinQuery()
                .orderBy(userImage.id.desc())
                .fetchFirst());
    }

    private JPAQuery<UserImageEntity> setFetchJoinQuery() {
        return jpaQuery.select(this.userImage)
                .from(this.userImage)
                .innerJoin(this.userImage.userEntity, this.user)
                .fetchJoin();
    }
}
