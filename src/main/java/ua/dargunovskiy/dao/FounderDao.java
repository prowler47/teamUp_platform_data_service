package ua.dargunovskiy.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.dargunovskiy.dto.UserRequestDto;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Participant;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.User;
import ua.dargunovskiy.util.AccessRightsUtil;

import java.util.List;
import java.util.UUID;

@Repository
public class FounderDao implements Dao<UUID, Founder>  {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void add(Founder founder) {
//        if (founder.getUser() == null || founder.getProjectId() == null || founder.getSecretCode() == null) {
//            throw new RuntimeException();
//        }
        Session session = entityManager.unwrap(Session.class);
        session.merge(founder);
    }

    @Override
    @Transactional
    public List<Founder> getAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Founder> query = session.createQuery("from Founder", Founder.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Founder update(Founder founderToUpdate, Founder founderForUpdate) {
        Session session = entityManager.unwrap(Session.class);
        founderToUpdate.setId(founderForUpdate.getId());
        founderToUpdate.setProjectId(founderForUpdate.getProjectId());
        founderToUpdate.setSecretCode(founderForUpdate.getSecretCode());
        founderToUpdate.setUserId(founderForUpdate.getUserId());
        session.merge(founderToUpdate);
        return founderToUpdate;
    }

    @Transactional
    public void updateProjectIdInFounderAfterDeleteProject(Founder founder) {
        Session session = entityManager.unwrap(Session.class);

        session.merge(founder);

    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Session session = entityManager.unwrap(Session.class);
        Founder founder = session.get(Founder.class, id);
        if (founder != null) {
            session.remove(founder);
        }
    }

    @Transactional
    public Founder getFounderById(UUID id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Founder.class, id);
    }

    @Transactional
    public void addNewParticipantToProject(UUID founderId, UserRequestDto userRequestDto) {
        Session session = entityManager.unwrap(Session.class);
        Founder founder = session.get(Founder.class, founderId);
        Project projectById = projectDao.getProjectById(userRequestDto.getProjectId());
        User userById = userDao.getUserById(userRequestDto.getUserId());
        if (AccessRightsUtil.ifAccessGranted(founder, projectById)) {
            Participant participant = new Participant();
            participant.setProject(projectById);
            participant.setRole(userRequestDto.getSpeciality());
            participant.setUser(userById);
            session.merge(participant);
        }
    }

}
