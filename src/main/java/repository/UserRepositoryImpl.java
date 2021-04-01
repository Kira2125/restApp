package repository;

import model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static util.Validation.checkNotFoundWithId;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
            em.persist(user);
            return user;

    }

    @Override
    @Transactional
    public User update(User user, int id) {
        Query query = em.createQuery("update User set name =:userName, email =:emailName, password =:passwordName where id = :idName");
        query.setParameter("userName", user.getName());
        query.setParameter("emailName", user.getEmail());
        query.setParameter("passwordName", user.getPassword());
        query.setParameter("idName", id);
        query.executeUpdate();
        return user;
    }

    @Override
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(em.createQuery("DELETE FROM User u WHERE u.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0, id);
    }

    @Override
    public User get(int id) {
        return checkNotFoundWithId(em.find(User.class, id), id);
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.name,u.email", User.class)
                .getResultList();
    }
}
