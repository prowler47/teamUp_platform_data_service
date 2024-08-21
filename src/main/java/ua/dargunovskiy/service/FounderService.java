package ua.dargunovskiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dargunovskiy.dao.FounderDao;
import ua.dargunovskiy.dao.ProjectDao;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.util.FounderSecretCodeGenerator;

import java.util.List;
import java.util.UUID;

@Service
public class FounderService {

    @Autowired
    private FounderDao founderDao;

    @Autowired
    private ProjectDao projectDao;

    public void addFounder(Founder founder) throws RuntimeException {
        founderDao.add(founder);
    }

    public void addFounder(Founder founder, UUID userId) {
        founder.setUserId(userId);
        founder.setSecretCode(FounderSecretCodeGenerator.secretCodeGenerator(founder));
        if (!isFounderDuplicate(userId)) {
            founderDao.add(founder);
        }
    }

    public void addProjectAsFounder(Project project, UUID founderId) {
        Founder founderById = founderDao.getFounderById(founderId);
        project.setSecretCode(founderById.getSecretCode());
        project.setFounderId(founderId);
        if (!isProjectDuplicate(founderId)) {
            projectDao.add(project);
            projectDao.setProjectIdFromProjectBySecretCodeToFounderById(founderById);
        }
    }

    public void deleteFounder(UUID founderId) {
        founderDao.delete(founderId);
    }

     private boolean isProjectDuplicate(UUID founderId) {
        List<Project> allProjects = projectDao.getAll();
        for (Project project : allProjects) {
            if (project.getFounderId().equals(founderId)) {
                return true;
            }
        }
        return false;
    }

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
