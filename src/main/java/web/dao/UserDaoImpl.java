package web.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import web.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(long id) {
        entityManager.createQuery("DELETE FROM User WHERE id=: id").setParameter("id", id).executeUpdate();
    }

    @Override
    public List<User> getAllUsers() {

        Session session = entityManager.unwrap(Session.class);

        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }
}


