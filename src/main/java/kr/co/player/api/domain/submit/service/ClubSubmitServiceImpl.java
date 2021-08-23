package kr.co.player.api.domain.submit.service;

import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.domain.shared.PageUtil;
import kr.co.player.api.domain.submit.model.ClubSubmitDto;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubSubmitEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubSubmitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubSubmitServiceImpl implements ClubSubmitService {

    private final ClubSubmitRepository clubSubmitRepository;

    private final static int LIMIT = 10;

    @Override
    public void createSubmit(ClubSubmitDto.CREATE create) {
        clubSubmitRepository.save(ClubSubmitEntity.builder()
                .clubEntity(create.getClubEntity())
                .userEntity(UserThreadLocal.get())
                .joinStatus(JoinStatus.of("WAITING"))
                .message(create.getMessage())
                .build());
    }

    @Override
    public Page<ClubSubmitEntity> getClubSubmitsByClubEntity(int pageNo, ClubEntity clubEntity) {
        return clubSubmitRepository.fetchClubSubmitByClubEntity(PageUtil.applyPageConfig(pageNo, LIMIT), clubEntity);
    }

    @Override
    public Long countClubSubmitByUserEntityNotWaiting() {
        return clubSubmitRepository.countClubSubmitByUserEntityAndNotWaiting(UserThreadLocal.get());
    }

    @Override
    public Optional<ClubSubmitEntity> getClubSubmitByWaiting(ClubEntity clubEntity) {
        return clubSubmitRepository.fetchClubSubmitByUserEntityAndClubEntityAndWaiting(UserThreadLocal.get(), clubEntity);
    }

    @Override
    public Optional<ClubSubmitEntity> getClubSubmitByWaiting(ClubEntity clubEntity, UserEntity userEntity) {
        return clubSubmitRepository.fetchClubSubmitByUserEntityAndClubEntityAndWaiting(userEntity, clubEntity);
    }

    /**
     * 내가 신청한 클럽 목록 조회 : ClubSubmitService
     * @param pageNo : 페이지 번호
     * @return 페이징 dto
     */
    @Override
    public Page<ClubSubmitDto.READ> getClubSubmits(int pageNo) {
        return clubSubmitRepository.fetchClubSubmitByUserEntity(PageUtil.applyPageConfig(pageNo, LIMIT), UserThreadLocal.get())
                .map(ClubSubmitEntity::toDomain);
    }

    /**
     * JoinStatus 에 따른 신청한 클럽 목록 조회 : ClubSubmitService
     * @param pageNo : 페이지 번호
     * @param joinStatusList : 신청 상태
     * @return 페이징 dto
     */
    @Override
    public Page<ClubSubmitDto.READ> getClubSubmitsByJoinStatus(int pageNo, List<String> joinStatusList) {
        List<JoinStatus> statusList = joinStatusList.stream()
                .map(joinStatus -> JoinStatus.of(joinStatus))
                .collect(Collectors.toList());

        return clubSubmitRepository.fetchClubSubmitByUserEntityAndJoinStatus(PageUtil.applyPageConfig(pageNo, LIMIT), UserThreadLocal.get(), statusList)
                .map(ClubSubmitEntity::toDomain);
    }

    @Override
    public void updateJoinStatus(ClubSubmitDto.UPDATE update) {
        ClubSubmitEntity clubSubmitEntity = clubSubmitRepository.fetchClubSubmitByUserEntityAndClubEntityAndWaiting(update.getUserEntity(), update.getClubEntity())
                .orElseThrow(() -> new NotFoundException("ClubSubmitEntity"));

        clubSubmitEntity.updateJoinStatus(update.getJoinStatus());
        clubSubmitRepository.save(clubSubmitEntity);
    }

}
