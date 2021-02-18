package Controller.Filters;

import Model.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", value = "/admin/*")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();


        Users Usession = (Users)session.getAttribute("user");

        if(Usession == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Users U = Users.getUser(Usession.getLogin());
        session.setAttribute("user", U);

        if(U.admin()) {
            chain.doFilter(request, response);
        } else {
            response.setStatus(403);
            response.sendRedirect(request.getContextPath() + "/auth/index");
        }

    }

    public void init(FilterConfig config) {

    }

}
