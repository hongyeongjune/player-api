package kr.co.player.api.domain.match.service;

import com.amazonaws.services.dynamodbv2.xspec.M;
import kr.co.player.api.domain.match.model.MatchDto;
import kr.co.player.api.domain.match.model.common.MatchStatus;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.shared.PageUtil;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;
import kr.co.player.api.infrastructure.persistence.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    private final static int LIMIT = 10;

    @Override
    public void createMatch(MatchDto.CREATE create) {
        matchRepository.save(MatchEntity.builder()
                .name(create.getName())
                .address(create.getAddress())
                .startTime(create.getStartTime())
                .endTime(create.getEndTime())
                .fieldName(create.getFieldName())
                .playerCount(1)
                .matchStatus(MatchStatus.RECRUITMENT)
                .homeClubEntity(create.getClubEntity())
                .homeLeaderUserEntity(create.getUserEntity())
                .homeScore(0)
                .build());
    }

    @Override
    public Optional<MatchEntity> getMatchEntityByAddressAndFieldNameAndStartTimeAndEndTime(Address address, String fieldName, LocalDateTime startTime, LocalDateTime endTime) {
        return matchRepository.fetchByAddressAndFieldNameAndStartTimeAndEndTime(address, fieldName, startTime, endTime);
    }

    @Override
    public Optional<MatchEntity> getMatchEntity(Long id) {
        return matchRepository.fetchById(id);
    }

    /**
     * 모든 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatches(int pageNo) {
        return matchRepository.fetchMatches(PageUtil.applyPageConfig(pageNo, LIMIT))
                .map(MatchEntity::toDomain);
    }

    /**
     * 키워드 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @param name : 제목
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatchesByName(int pageNo, String name) {
        return matchRepository.fetchByName(PageUtil.applyPageConfig(pageNo, LIMIT), name).map(MatchEntity::toDomain);
    }

    /**
     * 키워드 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @param clubName : 클럽 이름
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatchesByClubName(int pageNo, String clubName) {
        return matchRepository.fetchByClubName(PageUtil.applyPageConfig(pageNo, LIMIT), clubName).map(MatchEntity::toDomain);
    }

    /**
     * 키워드 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @param identity : 유저 아이디
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatchesByIdentity(int pageNo, String identity) {
        return matchRepository.fetchByIdentity(PageUtil.applyPageConfig(pageNo, LIMIT), identity).map(MatchEntity::toDomain);
    }

    /**
     * 키워드 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @param fieldName : 장소 이름
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatchesByFieldName(int pageNo, String fieldName) {
        return matchRepository.fetchByFieldName(PageUtil.applyPageConfig(pageNo, LIMIT), fieldName).map(MatchEntity::toDomain);
    }

    /**
     * 키워드 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @param city : 도시
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatchesByCity(int pageNo, String city) {
        return matchRepository.fetchByCity(PageUtil.applyPageConfig(pageNo, LIMIT), city).map(MatchEntity::toDomain);
    }

    /**
     * 키워드 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @param district : 지역
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatchesByDistrict(int pageNo, String district) {
        return matchRepository.fetchByDistrict(PageUtil.applyPageConfig(pageNo, LIMIT), district).map(MatchEntity::toDomain);
    }

    /**
     * 키워드 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @param address : 주소
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatchesByAddress(int pageNo, Address address) {
        return matchRepository.fetchByAddress(PageUtil.applyPageConfig(pageNo, LIMIT), address).map(MatchEntity::toDomain);
    }

    /**
     * 키워드 매칭 조회 : MatchService
     * @param pageNo : 페이지 번호
     * @param startTime : 시작 시간
     * @param endTime : 종료 시간
     * @return 페이징 dto
     */
    @Override
    public Page<MatchDto.READ> getMatchesByTime(int pageNo, LocalDateTime startTime, LocalDateTime endTime) {
        return matchRepository.fetchByTime(PageUtil.applyPageConfig(pageNo, LIMIT), startTime, endTime).map(MatchEntity::toDomain);
    }
}
