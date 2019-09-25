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
 * doPost - / - сохраняет пользователя.
 */
public class UserCreateServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        User result = null;
        Integer id = Integer.parseInt(req.getParameter("id"));
        if (id != null) {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            result = new User(id, name, login, email, LocalDateTime.now());
            if (this.service.add(result)) {
                resp.sendRedirect(String.format("%s/index.jsp", req.getContextPath()));
            } else {
                PrintWriter writer = new PrintWriter(resp.getOutputStream());
                writer.println("User is already exist.");
                writer.println("<form action = \"" + req.getContextPath() + "/index.jsp\" method=\"get\">"
                        + "<input type=\"submit\" value=\"go back.\">"
                        + "</form>");
                writer.flush();
            }
        }
    }
}