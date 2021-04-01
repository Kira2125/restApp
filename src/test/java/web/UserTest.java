package web;

import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import util.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.UserTestData.*;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void create() {
        User created = repository.save(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        assertThat(created).isEqualTo(newUser);
    }

    @Test
    public void delete() {
    repository.delete(FIRST_ID);
       assertThrows(NotFoundException.class, () -> repository.get(FIRST_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> repository.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = repository.get(FIRST_ID);
        assertThat(user).isEqualTo(user1);
    }

    @Test
    public void getNotFound() { assertThrows(NotFoundException.class, () -> repository.get(NOT_FOUND)); }

    @Test
   public void update() {

        User user = new User();
        user.setEmail("some@email.ru");
        user.setName("somename");
        user.setPassword("dsadas43");
        repository.update(user, FIRST_ID);
        assertThat(repository.get(FIRST_ID).getEmail()).isEqualTo(user.getEmail());
        assertThat(repository.get(FIRST_ID).getName()).isEqualTo(user.getName());
        assertThat(repository.get(FIRST_ID).getPassword()).isEqualTo(user.getPassword());
        assertThat(repository.get(FIRST_ID).getId()).isEqualTo(FIRST_ID);
    }

    @Test
    public void getAll() {
        List<User> all = repository.getAll();
        assertEquals(all, Arrays.asList(user1, user2, user3, user4, user5));
    }
}
