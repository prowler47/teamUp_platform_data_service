package ua.dargunovskiy.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.dargunovskiy.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public class UserDao implements Dao<UUID, User> {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(User user) {
        var session = entityManager.unwrap(Session.class);
        if (user.getFirstName() == null || user.getLastName() == null || user.getSpeciality() == null) {
            throw new RuntimeException();
        }
        session.merge(user);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        var query = entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Transactional
    public User getUserById(UUID id) {
        Session session = entityManager.unwrap(Session.class);
        if (session.get(User.class, id) == null) {
            throw new NullPointerException();
        }
        return session.get(User.class, id);
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
