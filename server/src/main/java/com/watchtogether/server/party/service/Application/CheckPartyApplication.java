package com.watchtogether.server.party.service.Application;

import com.watchtogether.server.exception.PartyException;
import com.watchtogether.server.exception.type.PartyErrorCode;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.entitiy.PartyMember;
import com.watchtogether.server.party.domain.model.LeavePartyForm;
import com.watchtogether.server.party.domain.repository.PartyMemberRepository;
import com.watchtogether.server.party.domain.repository.PartyRepository;
import com.watchtogether.server.party.service.impl.PartyServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckPartyApplication {
        private final PartyRepository partyRepository;

        private final PartyMemberRepository partyMemberRepository;

        private final PartyServiceImpl partyService;



    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void autoCheckParty() {
        checkParty();
        checkSendMessage();
        checkLeave();
    }

    public void selfCheckParty() {
        checkParty();
    }


    private void checkParty() {
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByPayDt(LocalDate.now());
        if (partyList.isEmpty()) {
            log.info("오늘 결제할 파티가 존재하지 않습니다.");
            return;

        } else {
            for (Party party : partyList) {
                // 결제 진행부분 삽입

                party.setPayDt(party.getPayDt().plusMonths(1));
                partyRepository.save(party);
                log.info("오늘 결제한 파티는" + party.getId() + "번 " + party.getTitle() + "입니다");


                // 결제 진행되었다고 메시지 전송

            }
        }
    }

    private void checkSendMessage() {
        // 1주일전 확인 메시지 발송
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByPayDt(LocalDate.now().minusWeeks(1));
        if (partyList.isEmpty()) {
            log.info("오늘 확인 메시지를 전송할 파티가 존재하지 않습니다.");
            return;
        } else {
            for (Party party : partyList) {
                // 있다면 partyMember의 check를 false로 바꿔준다.
                for (int i = 0; i < party.getMembers().size(); i++) {
                    String nickname = party.getMembers().get(i).getNickName();
                    Optional<PartyMember> partyMember = partyMemberRepository.findByNickNameAndParty(nickname, party);
                    if (partyMember.isEmpty()) {
                        throw new PartyException(PartyErrorCode.NOT_FOUND_USER);
                    }else {
                        partyMember.get().setAlertCheck(false);
                        partyMemberRepository.save(partyMember.get());
                        // 파티 갱신할껀지 말지 메시지 전송

                    }
                }
            }
        }
    }
    private void checkLeave(){
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByPayDt(LocalDate.now().minusDays(4));
        if (partyList.isEmpty()) {
            log.info("오늘 파티를 나갈 인원이 존재하지 않습니다.");
            return;
    }else {
            for (Party party : partyList) {
                for (int i = 0; i < party.getMembers().size(); i++) {
                    String nickname = party.getMembers().get(i).getNickName();
                    Optional<PartyMember> partyMember = partyMemberRepository.findByNickNameAndParty(nickname, party);
                    if (partyMember.isEmpty()) {
                        throw new PartyException(PartyErrorCode.NOT_FOUND_USER);
                    }else {
                        if (!partyMember.get().isAlertCheck()){
                            LeavePartyForm leavePartyForm = LeavePartyForm.builder()
                                    .partyId(party.getId())
                                    .nickName(nickname)
                                    .build();
                            partyService.leaveParty(leavePartyForm);
                        }
                    }
                }
            }
        }
    }

}
