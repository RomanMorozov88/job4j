package ru.job4j.crudservletwebapp.presentation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;
import ru.job4j.crudservletwebapp.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class ServletsTest {

    @Test
    public void whenUpdateUser() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        User testUser = new User(1, "Test_Name", "Test_Login",
                "Test_Password", "Test_Email", LocalDateTime.now(), "guest");
        validate.add(testUser);
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("New_Test_Name");
        when(req.getParameter("login")).thenReturn("Test_Login");
        when(req.getParameter("password")).thenReturn("Test_Password");
        when(req.getParameter("email")).thenReturn("Test_Email");
        new UserUpdateServlet().doPost(req, resp);
        assertThat(validate.findAll().iterator().next().getName(), is("New_Test_Name"));
    }

    @Test
    public void whenDeleteUser() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        User testUser = new User(1, "Test_Name", "Test_Login",
                "Test_Password", "Test_Email", LocalDateTime.now(), "guest");
        validate.add(testUser);
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("1");
        new UserServlet().doPost(req, resp);
        assertThat(validate.findAll().size(), is(0));
    }

}

class ValidateStub implements Validate {
    private final Map<Integer, User> store = new HashMap<>();

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (this.store.get(user.getId()) == null) {
            this.store.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        User buffer = this.store.getOrDefault(user.getId(), null);
        if (buffer != null) {
            String workString = user.getName();
            if (workString != null) {
                buffer.setName(workString);
            }
            workString = user.getLogin();
            if (workString != null) {
                buffer.setLogin(workString);
            }
            workString = user.getPassword();
            if (workString != null) {
                buffer.setPassword(workString);
            }
            workString = user.getEmail();
            if (workString != null) {
                buffer.setEmail(workString);
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        return (this.store.remove(id) != null);
    }

    @Override
    public boolean uploadImg(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.store.values());
    }

    @Override
    public User findById(int id) {
        return this.store.getOrDefault(id, null);
    }

    @Override
    public User isCredentional(String login, String password) {
        return null;
    }
}