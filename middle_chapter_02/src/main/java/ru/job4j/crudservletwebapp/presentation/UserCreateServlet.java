package ru.job4j.crudservletwebapp.presentation;

import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;
import ru.job4j.crudservletwebapp.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * doGet URL /create - Открывает форму для создания нового пользователя.
 * doPost - / - сохраняет пользователя.
 */
public class UserCreateServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println("<form action = \"/items/userscreate\" method=\"post\">"
                + "<input type=\"text\" name=\"id\" value=\"enter id\"/>"
                + "<input type=\"text\" name=\"name\" value=\"enter name\"/>"
                + "<input type=\"text\" name=\"login\" value=\"enter login\"/>"
                + "<input type=\"text\" name=\"email\" value=\"enter email\"/>"
                + "<input type=\"submit\" value=\"add\">"
                + "</form>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        User result = null;
        Integer id = Integer.parseInt(req.getParameter("id"));
        if (id != null) {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            result = new User(id, name, login, email, LocalDateTime.now());
            if (this.service.add(result)) {
                writer.println("User has been added.");
            } else {
                writer.println("User is already exist.");
            }
        }
        writer.println("<form action = \"/items/users\" method=\"get\">"
                + "<input type=\"submit\" value=\"go back.\">"
                + "</form>");
        writer.flush();
    }
}