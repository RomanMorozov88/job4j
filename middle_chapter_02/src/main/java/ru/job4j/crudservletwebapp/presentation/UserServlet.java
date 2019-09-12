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
import java.util.List;

public class UserServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        List<User> list = this.service.findAll();
        if (list.size() > 0) {
            writer.println("All users:");
            for (User u : list) {
                writer.println(u.toString());
            }
        } else {
            writer.println("List is empty.");
        }
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String action = req.getParameter("action");
        switch (action) {
            case "add":
                if (this.service.add(this.grabUser(req, 0))) {
                    writer.println("User has been added.");
                } else {
                    writer.println("User is already exist.");
                }
                break;
            case "update":
                if (this.service.update(this.grabUser(req, 1))) {
                    writer.println("User has been updated.");
                } else {
                    writer.println("Failed to update.");
                }
                break;
            case "delete":
                if (this.service.delete(Integer.parseInt(req.getParameter("id")))) {
                    writer.println("User has been deleted.");
                } else {
                    writer.println("Failed to delete.");
                }
                break;
            default:
                writer.println("Something wrong");
        }
        writer.flush();
    }

    /**
     * Метод, возвращающий объект класса User из данных HttpServletRequest req.
     * Если флаг newOrNot равен нулю- то добавляется дата создания.
     *
     * @param req
     * @param newOrNot
     * @return
     */
    private User grabUser(HttpServletRequest req, int newOrNot) {
        User result = null;
        Integer id = Integer.parseInt(req.getParameter("id"));
        if (id != null) {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            if (newOrNot == 0) {
                result = new User(id, name, login, email, LocalDateTime.now());
            } else {
                result = new User(id, name, login, email);
            }
        }
        return result;
    }
}