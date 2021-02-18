package Controller.Filters;

import Model.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/auth/*", filterName = "DefaultFilter")
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        String context = request.getContextPath();
        uri = uri.substring(context.length());
        if(session.getAttribute("user") == null && uri.compareTo("/connexion") != 0) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Users Usession = (Users)session.getAttribute("user");
        Users U = Users.getUser(Usession.getLogin());
        session.setAttribute("user", U);

        if(U.user()) {
            chain.doFilter(request, response);
        } else {
            response.setStatus(403);
            response.sendRedirect(request.getContextPath() + "/validation");
        }

    }

    public void init(FilterConfig config) {
    }

}
