package ru.job4j.crudservletwebapp.presentation;

import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;
import ru.job4j.crudservletwebapp.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * UsersServlet
 * doGet URL  /list - открывает таблицу со всеми пользователями.
 * doPost - удаляет пользоваттеля.
 * В каждой строку должна быть колонка с кнопками
 * (редактировать, удалить)
 */
public class UserServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        List<User> list = this.service.findAll();
        if (list.size() > 0) {
            writer.println("All users:");
            writer.println("<table border=\"1\">");
            writer.println("<tr>"
                    + "<td>id</td>"
                    + "<td>name</td>"
                    + "<td>login</td>"
                    + "<td>email</td>"
                    + "<td>create date</td>"
                    + "<td colspan=\"2\">actions</td>"
                    + "</tr>");
            for (User u : list) {
                writer.println("<tr>"
                        + "<td>" + u.getId() + "</td>"
                        + "<td>" + u.getName() + "</td>"
                        + "<td>" + u.getLogin() + "</td>"
                        + "<td>" + u.getEmail() + "</td>"
                        + "<td>" + u.getCreateDate() + "</td>"
                        + "<td> "
                        + "<form action = \"/items/usersupdate\" method=\"get\">"
                        + "<input type=\"hidden\" name=\"id\" value=\"" + u.getId() + "\"/>"
                        + "<input type=\"submit\" value=\"update\">"
                        + "</form>"
                        + "</td>"
                        + "<td>"
                        + "<form action = \"/items/users\" method=\"post\">"
                        + "<input type=\"hidden\" name=\"id\" value=\"" + u.getId() + "\"/>"
                        + "<input type=\"submit\" value=\"delete\">"
                        + "</form>"
                        + "</td>"
                        + "</tr>");
            }
            writer.println("</table>");
        } else {
            writer.println("List is empty.");
        }
        writer.println("<form action = \"/items/userscreate\" method=\"get\">"
                + "<input type=\"submit\" value=\"add new user.\">"
                + "</form>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (this.service.delete(Integer.parseInt(req.getParameter("id")))) {
            writer.println("User has been deleted.");
        } else {
            writer.println("Failed to delete.");
        }
        writer.println("<form action = \"/items/users\" method=\"get\">"
                + "<input type=\"submit\" value=\"go back.\">"
                + "</form>");
        writer.flush();
    }
}