package ru.job4j.crudservletwebapp.presentation;

import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * UsersServlet
 * doPost - удаляет пользоваттеля.
 * В каждой строку должна быть колонка с кнопками
 * (редактировать, удалить)
 */
public class UserServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        if (this.service.delete(Integer.parseInt(req.getParameter("id")))) {
            resp.sendRedirect(String.format("%s/index.jsp", req.getContextPath()));
        } else {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.println("Failed to delete.");
            writer.println("<form action = \"" + req.getContextPath() + "/index.jsp\" method=\"get\">"
                    + "<input type=\"submit\" value=\"go back.\">"
                    + "</form>");
            writer.flush();
        }
    }
}