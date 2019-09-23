package ru.job4j.crudservletwebapp.presentation;

import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;
import ru.job4j.crudservletwebapp.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * doGet URL /edit?id={userId} - открывает форму для редактирования с заполенными полями.
 * doPost - / - сохраняет пользователя.
 */
public class UserUpdateServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        Integer id = Integer.parseInt(req.getParameter("id"));
        User bufferUser = this.service.findById(id);
        writer.println("<form action = \"/items/usersupdate\" method=\"post\">"
                + id
                + "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>"
                + "<input type=\"text\" name=\"name\" value=\"" + bufferUser.getName() + "\"/>"
                + "<input type=\"text\" name=\"login\" value=\"" + bufferUser.getLogin() + "\"/>"
                + "<input type=\"text\" name=\"email\" value=\"" + bufferUser.getEmail() + "\"/>"
                + bufferUser.getCreateDate()
                + "<input type=\"submit\" value=\"update\">"
                + "</form>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        Integer id = Integer.parseInt(req.getParameter("id"));
        User result = this.service.findById(id);
        if (result != null) {
            result.setName(req.getParameter("name"));
            result.setLogin(req.getParameter("login"));
            result.setEmail(req.getParameter("email"));
            if (this.service.update(result)) {
                writer.println("User has been updated.");
            } else {
                writer.println("Failed to update.");
            }
        }
        writer.println("<form action = \"/items/users\" method=\"get\">"
                + "<input type=\"submit\" value=\"go back.\">"
                + "</form>");
        writer.flush();
    }
}