package kr.co.player.api.domain.integrated.service;

import kr.co.player.api.domain.integrated.model.MatchIntegratedDto;

public interface MatchIntegratedService {

    //create - service
    void createMatch(MatchIntegratedDto.CREATE create);

    //read - service
    MatchIntegratedDto.READ getMatchDetail(Long id);
}
