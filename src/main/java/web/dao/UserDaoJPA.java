package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoJPA implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    //        EntityManager entityManager = entityManagerFactory.createEntityManager(); - не нужно получать entityManager в каждом методе,
    //        так как JPA сам внедряет с помощью аннотации @PersistenceContext


    @Transactional(readOnly = true)
    @Override
    public List<User> index() {
        System.out.println("Метод index вызвался");
        return entityManager.createQuery("select p from User p", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public User show(int id) {
        System.out.println("Метод show вызвался");
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public void save(User user) {
        System.out.println("Метод save вызвался");
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void update(User user, int id) {
        System.out.println("Метод update вызвался");
        User userToBeUpdated = entityManager.find(User.class, id);

        userToBeUpdated.setName(user.getName());
        userToBeUpdated.setSurname(user.getSurname());
        userToBeUpdated.setEmail(user.getEmail());
    }

    @Transactional
    @Override
    public void delete(int id) {
        System.out.println("Метод delete  вызвался");
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
