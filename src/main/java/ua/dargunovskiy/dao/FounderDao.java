package ua.dargunovskiy.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Project;

import java.util.List;
import java.util.UUID;

@Repository
public class FounderDao implements Dao<UUID, Founder>  {

    @Autowired
    private EntityManager entityManager;

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
    public Founder update(Founder founderToUpdate, Founder founderForUpdate) { return null; }

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

}
