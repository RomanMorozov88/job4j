package ru.job4j.crudservletwebapp.presentation;

import ru.job4j.crudservletwebapp.logic.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {

    private static final Config CONFIG = Config.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        resp.setContentType("name=" + name);
        resp.setContentType("image/png");
        File file = new File(CONFIG.get("folderimg") + File.separator + name);
        try (
                FileInputStream in = new FileInputStream(file)
        ) {
            resp.getOutputStream().write(in.readAllBytes());
        }
    }
}