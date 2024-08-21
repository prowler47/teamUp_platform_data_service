package ua.dargunovskiy.dao;


import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Project;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;

@Repository
public class ProjectDao implements Dao<UUID, Project> {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Project project) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(project);
    }

    @Override
    @Transactional
    public List<Project> getAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Project> query = session.createQuery("from Project", Project.class);
        return query.getResultList();
    }

    @Override
    public Project update(Project entity) {
        return null;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Session session = entityManager.unwrap(Session.class);
        Project projectForRemove = session.get(Project.class, id);
        if (projectForRemove == null) {
            throw new RuntimeException();
        }
        session.remove(projectForRemove);
    }

    @Transactional
    public Project getProjectById(UUID projectId) {
        var session = entityManager.unwrap(Session.class);
        return session.get(Project.class, projectId);
    }

    @Transactional
    public void setProjectIdFromProjectBySecretCodeToFounderById(Founder founderById) {
        Session session = entityManager.unwrap(Session.class);
        Query<Project> query = session.createQuery("from Project", Project.class);
        List<Project> projectList = query.getResultList();
        projectList.stream().filter(e -> e.getSecretCode().equals(founderById.getSecretCode())).findFirst().ifPresent(project -> founderById.setProjectId(project.getId()));
    }
}
