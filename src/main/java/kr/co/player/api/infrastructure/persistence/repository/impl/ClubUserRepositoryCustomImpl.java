package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.co.player.api.domain.club.model.common.ClubUserRole;
import kr.co.player.api.infrastructure.persistence.entity.*;
import kr.co.player.api.infrastructure.persistence.repository.ClubUserRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class ClubUserRepositoryCustomImpl extends QuerydslRepositorySupport implements ClubUserRepositoryCustom {

    private final JPAQueryFactory jpaQuery;
    private final QClubEntity club = QClubEntity.clubEntity;
    private final QClubUserEntity clubUser = QClubUserEntity.clubUserEntity;
    private final QUserEntity user = QUserEntity.userEntity;

    public ClubUserRepositoryCustomImpl(JPAQueryFactory jpaQuery) {
        super(ClubUserEntity.class);
        this.jpaQuery = jpaQuery;
    }

    @Override
    public Optional<ClubUserEntity> fetchByClubEntityAndClubUserRole(ClubEntity clubEntity) {
        return Optional.ofNullable(setFetchJoinQuery()
                .where(eqClub(clubEntity)
                        .and(eqRole(ClubUserRole.of("LEADER"))))
                .fetchOne());
    }

    @Override
    public Long countByClubEntity(ClubEntity clubEntity) {
        return setFetchJoinQuery()
                .where(eqClub(clubEntity))
                .fetchCount();
    }

    @Override
    public Optional<ClubUserEntity> fetchByClubEntityAndUserEntity(ClubEntity clubEntity, UserEntity userEntity) {
        return Optional.ofNullable(setFetchJoinQuery()
                .where(eqClub(clubEntity)
                        .and(eqUser(userEntity)))
                .fetchOne());
    }

    @Override
    public List<ClubUserEntity> fetchByUserEntity(UserEntity userEntity) {
        return setFetchJoinQuery()
                .where(eqUser(userEntity))
                .fetch();
    }

    private JPAQuery<ClubUserEntity> setFetchJoinQuery() {
        return jpaQuery.selectFrom(this.clubUser)
                .innerJoin(this.clubUser.clubEntity, this.club)
                .innerJoin(this.clubUser.userEntity, this.user)
                .fetchJoin();
    }

    private BooleanExpression eqUser(UserEntity userEntity) {
        return this.clubUser.userEntity.id.eq(userEntity.getId());
    }

    private BooleanExpression eqClub(ClubEntity clubEntity) {
        return this.clubUser.clubEntity.id.eq(clubEntity.getId());
    }

    private BooleanExpression eqRole(ClubUserRole role) {
        return this.clubUser.clubUserRole.eq(role);
    }

}
