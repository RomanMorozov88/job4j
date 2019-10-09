package ru.job4j.crudservletwebapp.presentation;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.crudservletwebapp.logic.Config;
import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;
import ru.job4j.crudservletwebapp.models.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Этот сервлет будет обрабатывать загрузку файла на сервер.
 */
public class UploadServlet extends HttpServlet {

    private final Validate service = ValidateService.getInstance();
    private static final Config CONFIG = Config.getInstance();

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
            Integer id = null;
            String imgName = null;
            String idString = null;
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    imgName = item.getName();
                    File file = new File(folder + File.separator + imgName);
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                } else {
                    idString = item.getFieldName();
                    if (idString.equals("id")) {
                        id = Integer.parseInt(item.getString());
                    }
                }
            }
            User buffer = service.findById(id);
            String oldPhoto = buffer.getPhotoId();
            if (oldPhoto != null) {
                File forDeleting = new File(folder + File.separator + oldPhoto);
                forDeleting.delete();
            }
            buffer.setPhotoId(imgName);
            if (service.uploadImg(buffer)) {
                req.setAttribute("userForUpdate", buffer);
                req.getRequestDispatcher("/WEB-INF/views/userupdatepage.jsp").forward(req, resp);
            } else {
                req.setAttribute("messageForBack", "Upload error.");
                req.getRequestDispatcher("/WEB-INF/views/wayback.jsp").forward(req, resp);
            }
        } catch (FileUploadException e) {
            req.setAttribute("messageForBack", "Something is wrong.");
            req.getRequestDispatcher("/WEB-INF/views/wayback.jsp").forward(req, resp);
            e.printStackTrace();
        }
    }
}