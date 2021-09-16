package kr.co.player.api.application.match;

import kr.co.player.api.domain.club.service.ClubService;
import kr.co.player.api.domain.club.service.ClubUserService;
import kr.co.player.api.domain.integrated.model.MatchIntegratedDto;
import kr.co.player.api.domain.integrated.service.MatchIntegratedService;
import kr.co.player.api.domain.match.model.MatchDto;
import kr.co.player.api.domain.match.model.MatchUserDto;
import kr.co.player.api.domain.match.model.common.MatchType;
import kr.co.player.api.domain.match.model.common.MatchUserRole;
import kr.co.player.api.domain.match.service.MatchService;
import kr.co.player.api.domain.match.service.MatchUserService;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;
import kr.co.player.api.infrastructure.persistence.entity.MatchUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchIntegratedServiceImpl implements MatchIntegratedService {

    private final ClubService clubService;
    private final ClubUserService clubUserService;
    private final MatchService matchService;
    private final MatchUserService matchUserService;

    /**
     * 클럽에 가입된 회원이 축구 경기를 위한 매치 생성 : ClubService + ClubUserService + MatchService + MatchUserService
     * @param create : 매칭 생성 dto
     */
    @Override
    public void createMatch(MatchIntegratedDto.CREATE create) {
        UserEntity userEntity = UserThreadLocal.get();
        ClubEntity clubEntity = clubService.getClub(create.getClubName());

        if(!clubUserService.getClubUserEntity(clubEntity, userEntity).isPresent()) {
            throw new BadRequestException("해당 클럽에 가입된 회원이 아닙니다.");
        }

        LocalDateTime startTime = LocalDateTime.of(create.getYear(), create.getMonth(), create.getDay(), create.getStartHour(), create.getStartMinutes());
        LocalDateTime endTime = LocalDateTime.of(create.getYear(), create.getMonth(), create.getDay(), create.getEndHour(), create.getEndMinutes());
        Address address = new Address(create.getCity(), create.getDistrict());

        if(matchService.getMatchEntityByAddressAndFieldNameAndStartTimeAndEndTime(address, create.getFieldName(), startTime, endTime).isPresent()) {
            throw new BadRequestException("해당 장소와 시간에 생성된 매치가 이미 존재합니다. 다른 시간 혹은 장소를 선택해주세요.");
        }

        matchService.createMatch(MatchDto.CREATE.builder()
                .clubEntity(clubEntity)
                .userEntity(userEntity)
                .name(create.getName())
                .address(address)
                .startTime(startTime)
                .endTime(endTime)
                .fieldName(create.getFieldName())
                .build());

        matchUserService.createMatchUser(MatchUserDto.CREATE.builder()
                .userEntity(userEntity)
                .matchType(MatchType.HOME)
                .matchUserRole(MatchUserRole.USER)
                .build());
    }

    @Override
    public MatchIntegratedDto.READ getMatchDetail(Long id) {
        MatchEntity matchEntity = matchService.getMatchEntity(id).orElseThrow(() -> new NotFoundException("Match Entity"));
        List<MatchUserEntity> matchUserEntityList = matchUserService.getMatchUserByMatchEntity(matchEntity);

        return MatchIntegratedDto.READ.builder()
                .name(matchEntity.getName())
                .clubName(matchEntity.getHomeClubEntity().getClubName())
                .address(matchEntity.getAddress())
                .startTime(matchEntity.getStartTime())
                .endTime(matchEntity.getEndTime())
                .fieldName(matchEntity.getFieldName())
                .matchUserList(matchUserEntityList.stream()
                        .map(MatchUserEntity::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}
