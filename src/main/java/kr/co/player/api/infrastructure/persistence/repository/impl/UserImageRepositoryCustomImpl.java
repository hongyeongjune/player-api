package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.player.api.infrastructure.persistence.entity.QUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.QUserImageEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;
import kr.co.player.api.infrastructure.persistence.repository.UserImageRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
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

    @Override
    public List<UserImageEntity> fetchByUserEntity(UserEntity userEntity) {
        return setFetchJoinQuery()
                .where(eqUser(userEntity))
                .fetch();
    }

    @Override
    public Optional<UserImageEntity> fetchByUserEntityAndUrl(UserEntity userEntity, String url) {
        return Optional.ofNullable(setFetchJoinQuery()
                .where(eqUser(userEntity).and(this.userImage.url.eq(url)))
                .fetchOne());
    }

    private JPAQuery<UserImageEntity> setFetchJoinQuery() {
        return jpaQuery.select(this.userImage)
                .from(this.userImage)
                .innerJoin(this.userImage.userEntity, this.user)
                .fetchJoin();
    }

    private BooleanExpression eqUser(UserEntity userEntity) {
        return this.userImage.userEntity.id.eq(userEntity.getId());
    }
}
