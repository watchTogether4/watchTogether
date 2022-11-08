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


    @PostMapping("/accept")
    public ResponseEntity<?> acceptParty(@RequestBody AcceptPartyForm form){
       return ResponseEntity.ok(partyServiceimpl.acceptParty(form));
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinParty(@RequestBody JoinPartyForm form){
        return ResponseEntity.ok(partyServiceimpl.joinPartyAndCheckFull(form));
    }

    @GetMapping("/find-myParties")
    public ResponseEntity<?> myParties(@RequestBody FindMyPartiesForm form){
        return ResponseEntity.ok(partyServiceimpl.findMyParties(form));
    }

    @GetMapping("/showPartyList")
    public ResponseEntity<?> showPartyList(){
        return ResponseEntity.ok(partyServiceimpl.showPartyList());
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveParty(@RequestBody LeavePartyForm form){
        return ResponseEntity.ok(partyServiceimpl.leaveParty(form));
    }
    @PostMapping("/checkInviteMessage")
    public ResponseEntity<?> checkInviteMessage(CheckInviteMessageForm form){
        return ResponseEntity.ok(partyServiceimpl.checkInviteMessage(form));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(ChangePasswordForm form){
        return ResponseEntity.ok(partyServiceimpl.changePassword(form));
    }

}
