package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoList implements UserDao{

    private static int USER_ID;

    private List<User> users;

    {
        users = new ArrayList<>();
        users.add(new User(++USER_ID, "Tomas", "Shelby", "777"));
        users.add(new User(++USER_ID, "Elon", "Mask", "999"));
        users.add(new User(++USER_ID, "Rain", "Gosling", "12345"));

    }
    @Override
    public List<User> index() {
        return users;
    }

    @Override
    public User show(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public void save(User user) {
        user.setId(++USER_ID);
        users.add(user);
    }

    @Override
    public void update(User userUpdate, int id) {
        User userToBeUpdated = show(id);
        userToBeUpdated.setName(userUpdate.getName());
        userToBeUpdated.setSurname(userUpdate.getSurname());
        userToBeUpdated.setEmail(userUpdate.getEmail());

    }

    @Override
    public void delete(int id) {
        users.removeIf(user -> user.getId() == id);

    }
}
