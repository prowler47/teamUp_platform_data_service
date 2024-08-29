package ua.dargunovskiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dargunovskiy.dao.ParticipantDao;
import ua.dargunovskiy.dto.ParticipantDto;
import ua.dargunovskiy.entity.Participant;
import ua.dargunovskiy.util.ParticipantDtoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantDao participantDao;

    // add new participant to table participants
    public void addParticipant(Participant participant) throws RuntimeException {
        if (!isDuplicatesPresent(getAllParticipants(), participant)) {
            participantDao.add(participant);
        } else {
            System.out.println("true");
        }
    }

    // get full list of participants
    public List<Participant> getAllParticipants() {
       return participantDao.getAll();
    }

    public List<ParticipantDto> getAllParticipantsDtoFromProject(UUID projectId) {
        List<Participant> participantList = getAllParticipants().stream().filter(e -> e.getProject().getId().equals(projectId)).toList();
        List<ParticipantDto> participantDtoList = new ArrayList<>();
        for (Participant participant : participantList) {
            participantDtoList.add(ParticipantDtoUtil.fromParticipantToParticipantDto(participant));
        }
        return participantDtoList;
    }

    // get full list of participants with their projects
    public List<Participant> getAllParticipantsWithProjects() {
        List<Participant> listOfParticipantsWithProjects = new ArrayList<>();
        List<Participant> allParticipants = getAllParticipants();
        for (Participant participant : allParticipants) {
           Participant expandedParticipant =  new Participant();
           expandedParticipant.setId(participant.getId());
           expandedParticipant.setRole(participant.getRole());
           expandedParticipant.setProject(participant.getProject());
           listOfParticipantsWithProjects.add(expandedParticipant);
        }
        return listOfParticipantsWithProjects;
    }

    public void removeParticipantById(UUID id) throws RuntimeException {
        participantDao.delete(id);
    }

    // check if the same participant present in participants table
    private boolean isDuplicatesPresent(List<Participant> list, Participant participantForAdding) {
        for (Participant participant : list) {
            if (participant.getProject() != null) {
                if (participant.getProject().getId().equals(participantForAdding.getProject().getId()) &&
                        participant.getUser().getId().equals(participantForAdding.getUser().getId())) {
                    return true;
                }
            }
        }
       return false;
    }
}
