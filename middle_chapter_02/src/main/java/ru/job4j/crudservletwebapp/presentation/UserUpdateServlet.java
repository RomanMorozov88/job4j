package ru.job4j.crudservletwebapp.presentation;

import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;
import ru.job4j.crudservletwebapp.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * doPost - / - сохраняет пользователя.
 */
public class UserUpdateServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("userForUpdate", service.findById(id));
        req.getRequestDispatcher("/WEB-INF/views/userupdatepage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        Integer id = Integer.parseInt(req.getParameter("id"));
        User result = this.service.findById(id);
        if (result != null) {
            result.setName(req.getParameter("name"));
            result.setLogin(req.getParameter("login"));
            result.setEmail(req.getParameter("email"));
            if (this.service.update(result)) {
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else {
                req.setAttribute("messageForBack", "Failed to update.");
                req.getRequestDispatcher("/WEB-INF/views/wayback.jsp").forward(req, resp);
            }
        }
    }
}