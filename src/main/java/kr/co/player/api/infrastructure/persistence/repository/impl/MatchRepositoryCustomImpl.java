package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.*;
import kr.co.player.api.infrastructure.persistence.repository.MatchRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.Optional;

public class MatchRepositoryCustomImpl extends QuerydslRepositorySupport implements MatchRepositoryCustom {

    private final JPAQueryFactory jpaQuery;
    private final QMatchEntity match = QMatchEntity.matchEntity;
    private final QClubEntity homeClub = QClubEntity.clubEntity;
    private final QClubEntity awayClub = QClubEntity.clubEntity;
    private final QUserEntity homeUser = QUserEntity.userEntity;
    private final QUserEntity awayUser = QUserEntity.userEntity;

    public MatchRepositoryCustomImpl(JPAQueryFactory jpaQuery) {
        super(MatchEntity.class);
        this.jpaQuery = jpaQuery;
    }

    @Override
    public Optional<MatchEntity> fetchByAddressAndFieldNameAndStartTimeAndEndTime(Address address, String fieldName, LocalDateTime startTime, LocalDateTime endTime) {
        return Optional.ofNullable(setFetchJoinQuery()
                .where(betweenStartTime(startTime, endTime)
                        .or(betweenEndTime(startTime, endTime))
                        .and(eqAddress(address))
                        .and(eqFieldName(fieldName)))
                .fetchFirst());
    }

    private BooleanExpression eqAddress(Address address) {
        return this.match.address.eq(address);
    }

    private BooleanExpression eqFieldName(String fieldName) {
        return this.match.fieldName.eq(fieldName);
    }

    private BooleanExpression betweenStartTime(LocalDateTime from, LocalDateTime to) {
        return this.match.startTime.between(from, to);
    }

    private BooleanExpression betweenEndTime(LocalDateTime from, LocalDateTime to) {
        return this.match.endTime.between(from, to);
    }

    private JPAQuery<MatchEntity> setFetchJoinQuery() {
        return jpaQuery.selectFrom(this.match)
                .innerJoin(this.match.homeClubEntity, this.homeClub)
                .innerJoin(this.match.awayClubEntity, this.awayClub)
                .innerJoin(this.match.homeUserEntity, this.homeUser)
                .innerJoin(this.match.awayUserEntity, this.awayUser)
                .fetchJoin();
    }
}
