package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.*;
import kr.co.player.api.infrastructure.persistence.repository.ClubSubmitRepositoryCustom;
import org.hibernate.mapping.Join;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClubSubmitRepositoryCustomImpl extends QuerydslRepositorySupport implements ClubSubmitRepositoryCustom {

    private final JPAQueryFactory jpaQuery;
    private final QClubEntity club = QClubEntity.clubEntity;
    private final QUserEntity user = QUserEntity.userEntity;
    private final QClubSubmitEntity clubSubmit = QClubSubmitEntity.clubSubmitEntity;

    public ClubSubmitRepositoryCustomImpl(JPAQueryFactory jpaQuery) {
        super(ClubSubmitEntity.class);
        this.jpaQuery = jpaQuery;
    }

    @Override
    public Page<ClubSubmitEntity> fetchClubSubmitByUserEntity(Pageable pageable, UserEntity userEntity) {
        JPAQuery<ClubSubmitEntity> query = setFetchJoinQuery()
                .where(eqUser(userEntity));

        return listToPage(pageable, query);
    }

    @Override
    public Page<ClubSubmitEntity> fetchClubSubmitByUserEntityAndJoinStatus(Pageable pageable, UserEntity userEntity, List<JoinStatus> joinStatusList) {
        JPAQuery<ClubSubmitEntity> query = setFetchJoinQuery()
                .where(eqUser(userEntity)
                        .and(inJoinStatus(joinStatusList)));

        return listToPage(pageable, query);
    }

    @Override
    public Page<ClubSubmitEntity> fetchClubSubmitByClubEntity(Pageable pageable, ClubEntity clubEntity) {
        JPAQuery<ClubSubmitEntity> query = setFetchJoinQuery()
                .where(eqClub(clubEntity));

        return listToPage(pageable, query);
    }

    @Override
    public Optional<ClubSubmitEntity> fetchClubSubmitByUserEntityAndClubEntityAndWaiting(UserEntity userEntity, ClubEntity clubEntity) {
        return Optional.ofNullable(setFetchJoinQuery()
                .where(eqUser(userEntity)
                        .and(eqClub(clubEntity))
                        .and(eqJoinStatus(JoinStatus.WAITING)))
                .fetchOne());
    }

    @Override
    public long countClubSubmitByUserEntityAndNotWaiting(UserEntity userEntity) {
        return setFetchJoinQuery()
                .where(eqUser(userEntity)
                        .and(inJoinStatus(Arrays.asList(JoinStatus.ACCEPT, JoinStatus.REJECT, JoinStatus.CANCEL))))
                .fetchCount();
    }

    private JPAQuery<ClubSubmitEntity> setFetchJoinQuery() {
        return jpaQuery.selectFrom(this.clubSubmit)
                .innerJoin(this.clubSubmit.userEntity, this.user)
                .innerJoin(this.clubSubmit.clubEntity, this.club)
                .fetchJoin();
    }

    private BooleanExpression eqUser(UserEntity userEntity) {
        return this.clubSubmit.userEntity.id.eq(userEntity.getId());
    }

    private BooleanExpression eqClub(ClubEntity clubEntity) {
        return this.clubSubmit.clubEntity.id.eq(clubEntity.getId());
    }

    private BooleanExpression eqJoinStatus(JoinStatus joinStatus) {
        return this.clubSubmit.joinStatus.eq(joinStatus);
    }

    private BooleanExpression inJoinStatus(List<JoinStatus> joinStatusList) {
        return this.clubSubmit.joinStatus.in(joinStatusList);
    }

    private Page<ClubSubmitEntity> listToPage(Pageable pageable, JPAQuery<ClubSubmitEntity> query) {
        List<ClubSubmitEntity> clubSubmitEntityList = getQuerydsl()
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<>(clubSubmitEntityList, pageable, query.fetchCount());
    }
}
