package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.*;
import kr.co.player.api.infrastructure.persistence.repository.MatchRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public Page<MatchEntity> fetchMatches(Pageable pageable) {
        return listToPage(pageable, setFetchJoinQuery());
    }

    @Override
    public Page<MatchEntity> fetchByName(Pageable pageable, String name) {
        JPAQuery<MatchEntity> query = setFetchJoinQuery().where(containsName(name));
        return listToPage(pageable, query);
    }

    @Override
    public Page<MatchEntity> fetchByClubName(Pageable pageable, String clubName) {
        JPAQuery<MatchEntity> query = setFetchJoinQuery().where(containsClubName(clubName));
        return listToPage(pageable, query);
    }

    @Override
    public Page<MatchEntity> fetchByIdentity(Pageable pageable, String identity) {
        JPAQuery<MatchEntity> query = setFetchJoinQuery().where(containsIdentity(identity));
        return listToPage(pageable, query);
    }

    @Override
    public Page<MatchEntity> fetchByFieldName(Pageable pageable, String fieldName) {
        JPAQuery<MatchEntity> query = setFetchJoinQuery().where(containsFieldName(fieldName));
        return listToPage(pageable, query);
    }

    @Override
    public Page<MatchEntity> fetchByCity(Pageable pageable, String city) {
        JPAQuery<MatchEntity> query = setFetchJoinQuery().where(containsCity(city));
        return listToPage(pageable, query);
    }

    @Override
    public Page<MatchEntity> fetchByDistrict(Pageable pageable, String district) {
        JPAQuery<MatchEntity> query = setFetchJoinQuery().where(containsDistrict(district));
        return listToPage(pageable, query);
    }

    @Override
    public Page<MatchEntity> fetchByAddress(Pageable pageable, Address address) {
        JPAQuery<MatchEntity> query = setFetchJoinQuery().where(eqAddress(address));
        return listToPage(pageable, query);
    }

    @Override
    public Page<MatchEntity> fetchByTime(Pageable pageable, LocalDateTime startTime, LocalDateTime endTime) {
        JPAQuery<MatchEntity> query = setFetchJoinQuery().where(betweenStartTime(startTime, endTime).or(betweenEndTime(startTime, endTime)));
        return listToPage(pageable, query);
    }

    @Override
    public Optional<MatchEntity> fetchById(Long id) {
        return Optional.ofNullable(setFetchJoinQuery().where(eqId(id)).fetchFirst());
    }


    private BooleanExpression eqAddress(Address address) {
        return this.match.address.eq(address);
    }

    private BooleanExpression eqFieldName(String fieldName) {
        return this.match.fieldName.eq(fieldName);
    }

    private BooleanExpression eqId(Long id) {
        return this.match.id.eq(id);
    }

    private BooleanExpression betweenStartTime(LocalDateTime from, LocalDateTime to) {
        return this.match.startTime.between(from, to);
    }

    private BooleanExpression betweenEndTime(LocalDateTime from, LocalDateTime to) {
        return this.match.endTime.between(from, to);
    }

    private BooleanExpression containsName(String name) {
        return this.match.name.contains(name);
    }
    
    private BooleanExpression containsClubName(String clubName) {
        return this.match.homeClubEntity.clubName.contains(clubName);
    }
    
    private BooleanExpression containsIdentity(String identity) {
        return this.match.homeLeaderUserEntity.identity.contains(identity);
    }
    
    private BooleanExpression containsFieldName(String fieldName) {
        return this.match.fieldName.contains(fieldName);
    }
    
    private BooleanExpression containsCity(String city) {
        return this.match.address.city.eq(city);
    }

    private BooleanExpression containsDistrict(String district) {
        return this.match.address.district.eq(district);
    }
    
    private JPAQuery<MatchEntity> setFetchJoinQuery() {
        return jpaQuery.selectFrom(this.match)
                .innerJoin(this.match.homeClubEntity, this.homeClub)
                .innerJoin(this.match.awayClubEntity, this.awayClub)
                .innerJoin(this.match.homeLeaderUserEntity, this.homeUser)
                .innerJoin(this.match.awayLeaderUserEntity, this.awayUser)
                .fetchJoin();
    }

    private Page<MatchEntity> listToPage(Pageable pageable, JPAQuery<MatchEntity> query) {
        List<MatchEntity> matchEntityList = getQuerydsl()
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<>(matchEntityList, pageable, query.fetchCount());
    }
}
