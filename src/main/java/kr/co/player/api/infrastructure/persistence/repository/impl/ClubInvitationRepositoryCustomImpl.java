package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.*;
import kr.co.player.api.infrastructure.persistence.repository.ClubInvitationRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class ClubInvitationRepositoryCustomImpl extends QuerydslRepositorySupport implements ClubInvitationRepositoryCustom {

    private final JPAQueryFactory jpaQuery;
    private final QClubInvitationEntity clubInvitation = QClubInvitationEntity.clubInvitationEntity;
    private final QClubUserEntity clubUser = QClubUserEntity.clubUserEntity;
    private final QClubEntity club = QClubEntity.clubEntity;
    private final QUserEntity user = QUserEntity.userEntity;

    public ClubInvitationRepositoryCustomImpl(JPAQueryFactory jpaQuery) {
        super(ClubInvitationEntity.class);
        this.jpaQuery = jpaQuery;
    }

    @Override
    public Page<ClubInvitationEntity> fetchClubInvitationByClubEntity(Pageable pageable, ClubEntity clubEntity) {
        JPAQuery<ClubInvitationEntity> query = setFetchJoinQuery()
                .where(eqClub(clubEntity));

        return listToPage(pageable, query);
    }

    @Override
    public Page<ClubInvitationEntity> fetchClubInvitationByUserEntity(Pageable pageable, UserEntity userEntity) {
        JPAQuery<ClubInvitationEntity> query = setFetchJoinQuery()
                .where(eqUser(userEntity));

        return listToPage(pageable, query);
    }

    @Override
    public Page<ClubInvitationEntity> fetchClubInvitationByUserEntityAndJoinStatus(Pageable pageable, UserEntity userEntity, List<JoinStatus> joinStatusList) {
        JPAQuery<ClubInvitationEntity> query = setFetchJoinQuery()
                .where(eqUser(userEntity)
                        .and(inJoinStatus(joinStatusList)));

        return listToPage(pageable, query);
    }

    @Override
    public Optional<ClubInvitationEntity> fetchClubInvitationByClubUserEntityAndUserEntityAndWaiting(ClubUserEntity clubUserEntity, UserEntity userEntity) {
        return Optional.ofNullable(setFetchJoinQuery()
                .where(eqClubUser(clubUserEntity)
                        .and(eqUser(userEntity))
                        .and(eqJoinStatus(JoinStatus.WAITING))).fetchOne());
    }

    private JPAQuery<ClubInvitationEntity> setFetchJoinQuery() {
        return jpaQuery.selectFrom(this.clubInvitation)
                .innerJoin(this.clubInvitation.clubUserEntity, this.clubUser)
                .innerJoin(this.clubUser.userEntity, this.user)
                .innerJoin(this.clubUser.clubEntity, this.club)
                .innerJoin(this.clubInvitation.userEntity, this.user)
                .fetchJoin();
    }

    private BooleanExpression eqClubUser(ClubUserEntity clubUserEntity) {
        return this.clubInvitation.clubUserEntity.id.eq(clubUserEntity.getId());
    }

    private BooleanExpression eqClub(ClubEntity clubEntity) {
        return this.clubInvitation.clubUserEntity.clubEntity.id.eq(clubEntity.getId());
    }

    private BooleanExpression eqUser(UserEntity userEntity) {
        return this.clubInvitation.userEntity.id.eq(userEntity.getId());
    }

    private BooleanExpression eqJoinStatus(JoinStatus joinStatus) {
        return this.clubInvitation.joinStatus.eq(joinStatus);
    }

    private BooleanExpression inJoinStatus(List<JoinStatus> joinStatusList) {
        return this.clubInvitation.joinStatus.in(joinStatusList);
    }

    private Page<ClubInvitationEntity> listToPage(Pageable pageable, JPAQuery<ClubInvitationEntity> query) {
        List<ClubInvitationEntity> clubInvitationEntityList = getQuerydsl()
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<>(clubInvitationEntityList, pageable, query.fetchCount());
    }
}
