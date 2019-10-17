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
 * на страницу private/adminuserupdate.jsp
 */
public class RoleForUpdateFilter implements Filter {

    private final Validate service = ValidateService.getInstance();
    private final RolesVault vault = RolesVault.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getRequestURI().contains("/usersupdate") && request.getMethod().equals("GET")) {
            HttpSession session = request.getSession();
            synchronized (session) {
                if ((boolean) session.getAttribute("access")) {
                    Integer id = Integer.parseInt(req.getParameter("id"));
                    request.setAttribute("userForUpdate", service.findById(id));
                    request.setAttribute("allRoles", vault.getVault().keySet());
                    request.getRequestDispatcher("/WEB-INF/views/private/adminuserupdate.jsp").forward(req, resp);
                }
            }
        }
        filterChain.doFilter(request, resp);
    }

    @Override
    public void destroy() {

    }
}
