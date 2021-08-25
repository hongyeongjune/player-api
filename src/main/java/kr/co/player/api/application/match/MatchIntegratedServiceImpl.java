package kr.co.player.api.application.match;

import kr.co.player.api.domain.club.service.ClubService;
import kr.co.player.api.domain.club.service.ClubUserService;
import kr.co.player.api.domain.integrated.model.MatchIntegratedDto;
import kr.co.player.api.domain.integrated.service.MatchIntegratedService;
import kr.co.player.api.domain.match.model.MatchDto;
import kr.co.player.api.domain.match.service.MatchService;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.user.service.UserService;
import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchIntegratedServiceImpl implements MatchIntegratedService {

    private final UserService userService;
    private final ClubService clubService;
    private final ClubUserService clubUserService;
    private final MatchService matchService;


    /**
     * 클럽에 가입된 회원이 축구 경기를 위한 매치 생성
     * @param create
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
    }
}
