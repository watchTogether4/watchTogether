package com.watchtogether.server.party.controller;

import com.watchtogether.server.party.domain.model.AcceptPartyForm;
import com.watchtogether.server.party.domain.model.CreatePartyForm;
import com.watchtogether.server.party.domain.model.FindMyPartiesForm;
import com.watchtogether.server.party.domain.model.JoinPartyForm;
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
        return ResponseEntity.ok(partyServiceimpl.createParty(form));
    }

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

}
