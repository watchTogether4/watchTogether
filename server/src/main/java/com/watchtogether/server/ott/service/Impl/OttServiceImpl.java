package com.watchtogether.server.ott.service.Impl;

import static com.watchtogether.server.exception.type.OttErrorCode.NOT_FOUND_OTT;

import com.watchtogether.server.exception.OttException;
import com.watchtogether.server.ott.domain.dto.OttDto;
import com.watchtogether.server.ott.domain.repository.OttRepository;
import com.watchtogether.server.ott.service.OttService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OttServiceImpl implements OttService {

    private final OttRepository ottRepository;
    @Override
    public OttDto searchOtt(Long ottId) {
        return OttDto.fromEntity(
            ottRepository.findById(ottId).orElseThrow(() -> new OttException(NOT_FOUND_OTT)));
    }
}
