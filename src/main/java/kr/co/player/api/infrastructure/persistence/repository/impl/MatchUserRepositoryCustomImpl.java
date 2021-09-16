package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.player.api.infrastructure.persistence.entity.*;
import kr.co.player.api.infrastructure.persistence.repository.MatchUserRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MatchUserRepositoryCustomImpl extends QuerydslRepositorySupport implements MatchUserRepositoryCustom {

    private final JPAQueryFactory jpaQuery;
    private final QMatchUserEntity matchUser = QMatchUserEntity.matchUserEntity;
    private final QMatchEntity match = QMatchEntity.matchEntity;
    private final QUserEntity user = QUserEntity.userEntity;

    private final QClubEntity homeClub = QClubEntity.clubEntity;
    private final QClubEntity awayClub = QClubEntity.clubEntity;
    private final QUserEntity homeUser = QUserEntity.userEntity;
    private final QUserEntity awayUser = QUserEntity.userEntity;

    public MatchUserRepositoryCustomImpl(JPAQueryFactory jpaQuery) {
        super(MatchUserEntity.class);
        this.jpaQuery = jpaQuery;
    }

    @Override
    public List<MatchUserEntity> fetchByMatchEntity(MatchEntity matchEntity) {
        return setFetchJoinQuery()
                .where(eqMatch(matchEntity))
                .fetch();
    }

    private BooleanExpression eqMatch(MatchEntity matchEntity) {
        return this.matchUser.matchEntity.id.eq(matchEntity.getId());
    }

    public JPAQuery<MatchUserEntity> setFetchJoinQuery() {
        return jpaQuery.selectFrom(this.matchUser)
                .innerJoin(this.matchUser.userEntity, this.user)
                .innerJoin(this.matchUser.matchEntity, this.match)
                .innerJoin(this.match.homeClubEntity)
                .innerJoin(this.match.awayClubEntity)
                .innerJoin(this.match.homeLeaderUserEntity)
                .innerJoin(this.match.awayLeaderUserEntity)
                .fetchJoin();
    }

}
