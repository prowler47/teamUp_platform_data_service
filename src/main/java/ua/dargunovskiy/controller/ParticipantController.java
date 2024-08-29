package ua.dargunovskiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.dargunovskiy.dto.ParticipantDto;
import ua.dargunovskiy.entity.Participant;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.service.ParticipantService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping("/addParticipant")
    public void addParticipant(@RequestBody Participant user) {
//        try {
            participantService.addParticipant(user);
//        } catch (RuntimeException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
    }

    @GetMapping("/getAllParticipants")
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/getAllParticipantsDtoFromProject/{projectId}")
    public List<ParticipantDto> getAllParticipantDtoFromProject(@PathVariable("projectId") UUID projectId) {
        return participantService.getAllParticipantsDtoFromProject(projectId);
    }

    @DeleteMapping("/removeParticipant/{id}")
    public void removeParticipantById(@PathVariable("id") UUID id) {
        try {
            participantService.removeParticipantById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
