package ru.job4j.crudservletwebapp.presentation;

import ru.job4j.crudservletwebapp.logic.Validate;
import ru.job4j.crudservletwebapp.logic.ValidateService;
import ru.job4j.crudservletwebapp.persistent.RolesVault;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Фильтр, отвечающий за перенаправление пользователся с ролью admin
 * на страницу private/adminusersview.jsp
 */
public class RoleForMainPageFilter implements Filter {

    private final Validate service = ValidateService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getRequestURI().equals(String.format("%s/", request.getContextPath()))
                && request.getMethod().equals("GET")) {
            HttpSession session = request.getSession();
            synchronized (session) {
                if ((boolean) session.getAttribute("access")) {
                    Integer id = (Integer) session.getAttribute("id");
                    request.setAttribute("mainUser", service.findById(id));
                    request.setAttribute("users", service.findAll());
                    request.getRequestDispatcher("/WEB-INF/views/private/adminusersview.jsp").forward(req, resp);
                }
            }
        }
        filterChain.doFilter(request, resp);
    }

    @Override
    public void destroy() {

    }

}