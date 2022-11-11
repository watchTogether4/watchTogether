package com.watchtogether.server.party.controller;

import com.watchtogether.server.party.domain.model.*;
import com.watchtogether.server.party.service.Application.CheckPartyApplication;
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

    private final CheckPartyApplication checkPartyApplication;


    @PostMapping("/create")
    public ResponseEntity<?> createParty(@RequestBody CreatePartyForm form){
        return ResponseEntity.ok(partyServiceimpl.createPartyAndSendInviteAlert(form));
    }


    @PostMapping("/accept")
    public ResponseEntity<?> acceptParty(@RequestBody AcceptPartyForm form){
        return ResponseEntity.ok(checkPartyApplication.acceptParty(form));
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinParty(@RequestBody JoinPartyForm form){
        return ResponseEntity.ok(checkPartyApplication.joinPartyAndCheckFull(form));
    }

    @PostMapping("/find-myParties")
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
    public ResponseEntity<?> checkInviteMessage(@RequestBody CheckInviteMessageForm form){
        return ResponseEntity.ok(partyServiceimpl.checkInviteMessage(form));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordForm form){
        return ResponseEntity.ok(partyServiceimpl.changePassword(form));
    }

    @PostMapping("/1")
    public ResponseEntity<?> checkSendMessage(){
        return ResponseEntity.ok(checkPartyApplication.checkSendMessage());
    }
    @PostMapping("/2")
    public ResponseEntity<?> checkLeave(){
        return ResponseEntity.ok(checkPartyApplication.checkLeave());
    }
    @PostMapping("/3")
    public ResponseEntity<?> checkParty(){
        return ResponseEntity.ok(checkPartyApplication.checkParty());
    }
    @PostMapping("/4")
    public ResponseEntity<?> checkCreatedParty(){
        return ResponseEntity.ok(checkPartyApplication.checkCreatedParty());
    }

}
