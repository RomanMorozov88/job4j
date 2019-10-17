package ru.job4j.crudservletwebapp.presentation;

import ru.job4j.crudservletwebapp.logic.Config;
import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * UsersServlet
 * doPost - удаляет пользоваттеля.
 * В каждой строку должна быть колонка с кнопками
 * (редактировать, удалить)
 */
public class UserServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();
    private static final Config CONFIG = Config.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        Integer id = (Integer) session.getAttribute("id");
        req.setAttribute("mainUser", service.findById(id));
        req.setAttribute("users", service.findAll());
        req.getRequestDispatcher("/WEB-INF/views/usersview.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        Integer id = Integer.parseInt(req.getParameter("id"));
        String deletingPhoto = this.service.findById(id).getPhotoId();
        if (this.service.delete(id)) {
            File oldPhoto = new File(CONFIG.get("folderimg") + File.separator + deletingPhoto);
            oldPhoto.delete();
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else {
            req.setAttribute("messageForBack", "Failed to delete.");
            req.getRequestDispatcher("/WEB-INF/views/wayback.jsp").forward(req, resp);
        }
    }
}