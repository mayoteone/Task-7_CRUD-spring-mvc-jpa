package web.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import java.util.List;

@Repository
public class UserDaoHibernate implements UserDao {

    private SessionFactory sessionFactory;

//    @Autowired
//    public UserDaoHibernate(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }


    @Transactional(readOnly = true)
    @Override
    public List<User> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from User p", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public User show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Transactional
    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Transactional
    @Override
    public void update(User user, int id) {
        Session session = sessionFactory.getCurrentSession();
        User userToBeUpdated = session.get(User.class, id);

        userToBeUpdated.setName(user.getName());
        userToBeUpdated.setSurname(user.getSurname());
        userToBeUpdated.setEmail(user.getEmail());
    }

    @Transactional
    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        session.delete(user);
    }
}
