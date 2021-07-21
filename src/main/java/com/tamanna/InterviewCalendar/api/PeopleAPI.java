package com.tamanna.InterviewCalendar.api;


import com.tamanna.InterviewCalendar.api.model.People;
import com.tamanna.InterviewCalendar.api.model.PeoppleEnum;
import com.tamanna.InterviewCalendar.controller.PeopleService;
import com.tamanna.InterviewCalendar.controller.repository.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("people")
@Validated
public class PeopleAPI {

    private final PeopleService controller;

    PeopleAPI( PeopleService controller) {
        this.controller = controller;
    }

    @PostMapping("availability")
    public ResponseEntity<String> setCandidateAvailability( @RequestBody @Valid People people, @PathParam("peopleType") String peopleType, BindingResult result){
        people.setPeopleType(PeoppleEnum.getIndPeopleType(peopleType));
        controller.addOnAgenda(people);

        return ResponseEntity.ok().build();
    }

    @GetMapping("slots/{candidate}")
    public ResponseEntity<Map<String, List<Slots>>> getPossibleSlots(@PathVariable("candidate") String candidateName){
        if(controller.hasCandidate( candidateName )) {
            Map<String, List<Slots>> slots = controller.findSlots( candidateName );

            return ResponseEntity.ok(slots);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("clean")
    public ResponseEntity<String> cleanAgenda(){
        controller.cleanAgenda();
        return ResponseEntity.ok().build();
    }

    @GetMapping("slots")
    public ResponseEntity<Agenda> getPossibleSlots() {
        Agenda agenda = controller.getAllAgenda();

        return ResponseEntity.ok(agenda);
    }
}
