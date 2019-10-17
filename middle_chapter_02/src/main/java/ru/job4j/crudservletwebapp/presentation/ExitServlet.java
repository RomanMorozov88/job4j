package ru.job4j.crudservletwebapp.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        synchronized (session) {
            synchronized (session) {
                session.invalidate();
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/loginview.jsp").forward(req, resp);
    }

}