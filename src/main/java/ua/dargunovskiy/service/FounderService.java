package ua.dargunovskiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dargunovskiy.dao.FounderDao;
import ua.dargunovskiy.dao.ProjectDao;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.util.AccessRightsUtil;
import ua.dargunovskiy.util.FounderSecretCodeGenerator;

import java.util.List;
import java.util.UUID;

@Service
public class FounderService {

    @Autowired
    private FounderDao founderDao;

    @Autowired
    private ProjectDao projectDao;

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

    // delete founder by id (for development using)

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
        }
    }

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
}
