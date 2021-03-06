package kr.co.player.api.infrastructure.persistence.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.QClubEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ClubRepositoryCustomImpl extends QuerydslRepositorySupport implements ClubRepositoryCustom {

    private final JPAQueryFactory jpaQuery;
    private final QClubEntity club = QClubEntity.clubEntity;

    public ClubRepositoryCustomImpl(JPAQueryFactory jpaQuery) {
        super(ClubEntity.class);
        this.jpaQuery = jpaQuery;
    }

    @Override
    public Page<ClubEntity> fetchClubs(Pageable pageable) {
        return listToPage(pageable, jpaQuery.selectFrom(this.club));
    }

    @Override
    public Page<ClubEntity> fetchClubsByAddress(Pageable pageable, List<String> districtList, List<String> cityList) {
        JPAQuery<ClubEntity> query = jpaQuery.selectFrom(this.club)
                .where(inDistrict(districtList)
                        .or(inCity(cityList)));

        return listToPage(pageable, query);
    }

    @Override
    public Page<ClubEntity> fetchClubsByKeywordContains(Pageable pageable, String keyword) {
        JPAQuery<ClubEntity> query = jpaQuery.selectFrom(this.club)
                .where(this.club.clubName.contains(keyword));

        return listToPage(pageable, query);
    }

    private BooleanExpression inDistrict(List<String> districtList) {
        return this.club.address.district.in(districtList);
    }

    private BooleanExpression inCity(List<String> cityList) {
        return this.club.address.city.in(cityList);
    }

    private Page<ClubEntity> listToPage(Pageable pageable, JPAQuery<ClubEntity> query) {
        List<ClubEntity> clubEntityList = getQuerydsl()
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<>(clubEntityList, pageable, query.fetchCount());
    }
}
