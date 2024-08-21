package ua.dargunovskiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dargunovskiy.dao.ParticipantDao;
import ua.dargunovskiy.entity.Participant;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantDao participantDao;

    public void addParticipant(Participant participant) throws RuntimeException {
        if (!isDuplicatesPresent(getAllParticipants(), participant)) {
            participantDao.add(participant);
        } else {
            System.out.println("true");
        }
    }

    public List<Participant> getAllParticipants() {
       return participantDao.getAll();
    }

    public List<Participant> getAllParticipantsWithProjects() {
        List<Participant> listOfParticipantsWithProjects = new ArrayList<>();
        List<Participant> allParticipants = getAllParticipants();
        for (Participant participant : allParticipants) {
           Participant expandedPArticipant =  new Participant();
           expandedPArticipant.setId(participant.getId());
           expandedPArticipant.setRole(participant.getRole());
           expandedPArticipant.setProject(participant.getProject());
           listOfParticipantsWithProjects.add(expandedPArticipant);
        }
        return listOfParticipantsWithProjects;
    }

    public void removeParticipantById(UUID id) throws RuntimeException {
        participantDao.delete(id);
    }

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
