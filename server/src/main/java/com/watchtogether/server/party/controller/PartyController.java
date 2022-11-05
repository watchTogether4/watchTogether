package com.watchtogether.server.party.controller;

import com.watchtogether.server.party.domain.model.*;
import com.watchtogether.server.party.service.impl.PartyServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "파티(PartyController)", description = "Party API")
@RequestMapping("/api/v1/parties")
public class PartyController {
    private final PartyServiceImpl partyServiceimpl;


    @PostMapping("/create")
    public ResponseEntity<?> createParty(@RequestBody CreatePartyForm form){
        return ResponseEntity.ok(partyServiceimpl.createPartyAndSendInviteAlert(form));
    }
    // todo 알림 api를 위해 초대한 사람의 유저 닉네임, 초대한 사람의 uuid 파티 아이디 전달 필요  :  create 함수를 따로 빼서 전달

    @PostMapping("/accept")
    public ResponseEntity<?> acceptParty(@RequestBody AcceptPartyForm form){
       return ResponseEntity.ok(partyServiceimpl.acceptParty(form));
    }

    @GetMapping("/find-myParties")
    public ResponseEntity<?> myParties(@RequestBody FindMyPartiesForm form){
        return ResponseEntity.ok(partyServiceimpl.findMyParties(form));
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinParty(@RequestBody JoinPartyForm form){
        return ResponseEntity.ok(partyServiceimpl.joinPartyAndCheckFull(form));
    }

    @GetMapping("/showPartyList")
    public ResponseEntity<?> showPartyList(){
        return ResponseEntity.ok(partyServiceimpl.showPartyList());
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveParty(@RequestBody LeavePartyForm form){
        return ResponseEntity.ok(partyServiceimpl.leaveParty(form));
    }

}
