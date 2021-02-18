package Controller.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/connexion", filterName = "ConnexionFilter")
public class ConnexionFilter implements Filter {
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        if(session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/auth/index");
            return;
        }

        chain.doFilter(request, response);
    }

}
