package ua.dargunovskiy.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.dargunovskiy.entity.Participant;
import ua.dargunovskiy.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public class ParticipantDao implements Dao<UUID, Participant> {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Participant participant) {
        Session session = entityManager.unwrap(Session.class);
        if (participant.getRole() == null) {
            throw new RuntimeException();
        }
        session.merge(participant);
    }

    @Override
    @Transactional
    public List<Participant> getAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Participant> query = session.createQuery("from Participant", Participant.class);
        return query.getResultList();
    }

    @Override
    public Participant update(Participant entity) {
        return null;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Session session = entityManager.unwrap(Session.class);
        Participant participantForDelete = session.get(Participant.class, id);
        if (participantForDelete == null) {
            throw new RuntimeException();
        }
        session.remove(participantForDelete);
    }
}
