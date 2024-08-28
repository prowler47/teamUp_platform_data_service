package ua.dargunovskiy.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.UserRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRequestDao implements Dao<UUID, UserRequest> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProjectDao projectDao;

    @Override
    @Transactional
    public void add(UserRequest userRequest) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(userRequest);
    }

    @Override
    @Transactional
    public List<UserRequest> getAll() {
        Query<UserRequest> query = entityManager.unwrap(Session.class).createQuery("from UserRequest", UserRequest.class);
        return query.getResultList();
    }

    @Transactional
    public List<UserRequest> getALLRequestsByProjectId(UUID projectId) {
      return getAll().stream().filter(e -> e.getProjectId().equals(projectId)).collect(Collectors.toList());
    }

    @Override
    public UserRequest update(UserRequest entityToUpdate, UserRequest entityForUpdate) {
        return null;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Session session = entityManager.unwrap(Session.class);
        UserRequest userRequestForDelete = session.get(UserRequest.class, id);
        if (userRequestForDelete != null) {
            session.remove(userRequestForDelete);
        }
    }

    @Transactional
    public void deleteByProjectId(UUID projectId) {
        Session session = entityManager.unwrap(Session.class);
        for (UserRequest userRequest : getAll()) {
            if (userRequest.getProjectId().equals(projectId)) {
                session.remove(userRequest);
            }

        }
    }
}
