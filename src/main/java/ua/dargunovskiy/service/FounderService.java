package ua.dargunovskiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dargunovskiy.dao.FounderDao;
import ua.dargunovskiy.dao.ParticipantDao;
import ua.dargunovskiy.dao.ProjectDao;
import ua.dargunovskiy.dao.UserRequestDao;
import ua.dargunovskiy.dto.UserRequestDto;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Participant;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.UserRequest;
import ua.dargunovskiy.util.AccessRightsUtil;
import ua.dargunovskiy.util.FounderSecretCodeGenerator;
import ua.dargunovskiy.util.UserRequestsDtoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FounderService {

    @Autowired
    private FounderDao founderDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserRequestDao userRequestDao;

    @Autowired
    private ParticipantDao participantDao;

    // add new founder (for development using)
    public void addFounder(Founder founder) throws RuntimeException {
        founderDao.add(founder);
    }

    // add new founder (for user using)
    public void addFounder(Founder founder, UUID userId) {
        founder.setUserId(userId);
        founder.setSecretCode(FounderSecretCodeGenerator.secretCodeGenerator(founder));
        if (!isFounderDuplicate(userId)) {
            founderDao.add(founder);
        }
    }

    public void deleteFounder(UUID founderId) {
        founderDao.delete(founderId);
    }

    // ---------------------- as founder section: -----------------------
    public void addProjectAsFounder(Project project, UUID founderId) {
        Founder founderById = founderDao.getFounderById(founderId);
        project.setSecretCode(founderById.getSecretCode());
        project.setFounderId(founderId);
        if (!isProjectDuplicate(founderId)) {
            projectDao.add(project);
            projectDao.setProjectIdFromProjectBySecretCodeToFounderById(founderById);
        }
    }

    public void updateFounder(Founder founderToUpdate, Founder founderForUpdate) {
        founderDao.update(founderToUpdate, founderForUpdate);
    }

    public void updateProjectAsFounder(UUID founderId, UUID projectId, Project projectForUpdate) {
        Project projectToUpdate = projectDao.getProjectById(projectId);
        Founder founderById = founderDao.getFounderById(founderId);
        if (AccessRightsUtil.ifAccessGranted(founderById, projectToUpdate)) {
            projectDao.update(projectToUpdate, projectForUpdate);
        }
    }

    public void deleteProjectAsFounder(UUID founderId, UUID projectId) {
        Founder founderById = founderDao.getFounderById(founderId);
        Project projectById = projectDao.getProjectById(projectId);
        if (AccessRightsUtil.ifAccessGranted(founderById, projectById)) {
            projectDao.delete(projectId);
            founderDao.delete(founderId);
            userRequestDao.deleteByProjectId(projectId);
        }
    }

    public void addNewParticipantToProjectAsFounder(UUID founderId, UserRequestDto userRequestDto) {
        if (!isParticipantDuplicate(userRequestDto)) {
            founderDao.addNewParticipantToProject(founderId, userRequestDto);
            userRequestDao.delete(userRequestDto.getId());
        }
    }

    public void deleteParticipant(UUID founderId, UUID participantId) {
        Founder founderById = founderDao.getFounderById(founderId);
        Participant participantById = participantDao.getParticipantById(participantId);
        if (AccessRightsUtil.ifAccessGranted(founderById, participantById.getProject())) {
            participantDao.delete(participantId);
        }
    }

    public List<UserRequest> getAllUserRequestByFounderId(UUID founderId) {
        UUID projectId = founderDao.getFounderById(founderId).getProjectId();
        return userRequestDao.getALLRequestsByProjectId(projectId);
    }

    public List<UserRequestDto> getAllUserRequestsDtoByFounderId(UUID founderId) {
        UUID projectId = null;
        if (founderDao.getFounderById(founderId) != null) {
            projectId = founderDao.getFounderById(founderId).getProjectId();
        }
        List<UserRequest> allRequestsByProjectId = userRequestDao.getALLRequestsByProjectId(projectId);
        List<UserRequestDto> userRequestDtoList = new ArrayList<>();
        for (UserRequest userRequest : allRequestsByProjectId) {
            userRequestDtoList.add(UserRequestsDtoUtil.fromUserRequestsToUserRequestsDto(userRequest));
        }
        return userRequestDtoList;
    }


    // --------------------------- auxiliary methods section: -------------------------------------

    // check if the same project is present in table projects by founder_id field
     private boolean isProjectDuplicate(UUID founderId) {
        List<Project> allProjects = projectDao.getAll();
        for (Project project : allProjects) {
            if (project.getFounderId().equals(founderId)) {
                return true;
            }
        }
        return false;
    }

    // check is the same founder is present in founders table by user_id field
    private boolean isFounderDuplicate(UUID userId) {
        List<Founder> allFounders = founderDao.getAll();
        for (Founder founder : allFounders) {
            if (founder.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private boolean isParticipantDuplicate(UserRequestDto userRequestDto) {
        List<Participant> participantList = participantDao.getAll();
        for (Participant participant : participantList) {
            if (participant.getUser().getId().equals(userRequestDto.getUserId())) {
                return true;
            }
        }
        return false;
    }
}
