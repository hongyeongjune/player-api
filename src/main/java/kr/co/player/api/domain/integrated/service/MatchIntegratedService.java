package kr.co.player.api.domain.integrated.service;

import kr.co.player.api.domain.integrated.model.MatchIntegratedDto;

public interface MatchIntegratedService {

    //create
    void createMatch(MatchIntegratedDto.CREATE create);
}
