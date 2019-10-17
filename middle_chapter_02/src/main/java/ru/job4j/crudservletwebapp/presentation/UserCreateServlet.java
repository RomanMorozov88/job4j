package ru.job4j.crudservletwebapp.presentation;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.crudservletwebapp.logic.Config;
import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;
import ru.job4j.crudservletwebapp.models.User;
import ru.job4j.crudservletwebapp.persistent.RolesVault;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * doPost - / - сохраняет пользователя.
 */
public class UserCreateServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();
    private final RolesVault vault = RolesVault.getInstance();
    private static final Config CONFIG = Config.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("allRoles", vault.getVault().keySet());
        req.getRequestDispatcher("/WEB-INF/views/usercreatepage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File(CONFIG.get("folderimg"));
            if (!folder.exists()) {
                folder.mkdir();
            }
            String field = null;
            String imgName = null;
            Integer id = null;
            String name = null;
            String login = null;
            String password = null;
            String email = null;
            String rolename = "guest";
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    imgName = item.getName();
                    if (!imgName.equals("")) {
                        File file = new File(folder + File.separator + imgName);
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            out.write(item.getInputStream().readAllBytes());
                        }
                    }
                } else {
                    field = item.getFieldName();
                    if (field.equals("id")) {
                        id = Integer.parseInt(item.getString());
                    } else if (field.equals("name")) {
                        name = item.getString();
                    } else if (field.equals("login")) {
                        login = item.getString();
                    } else if (field.equals("password")) {
                        password = item.getString();
                    } else if (field.equals("email")) {
                        email = item.getString();
                    } else if (field.equals("role")) {
                        rolename = item.getString();
                    }
                }
            }
            User buffer = new User(id, name, login, password, email, LocalDateTime.now(), rolename);
            buffer.setPhotoId(imgName);
            if (service.add(buffer)) {
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else {
                req.setAttribute("messageForBack", "Add error.");
                req.getRequestDispatcher("/WEB-INF/views/wayback.jsp").forward(req, resp);
            }
        } catch (FileUploadException e) {
            req.setAttribute("messageForBack", "Something is wrong.");
            req.getRequestDispatcher("/WEB-INF/views/wayback.jsp").forward(req, resp);
            e.printStackTrace();
        }
    }
}