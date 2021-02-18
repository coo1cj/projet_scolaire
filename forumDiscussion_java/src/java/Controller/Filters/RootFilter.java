package Controller.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "RessourcesFilter", urlPatterns = {"/*"})
public class RootFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String context = request.getContextPath();
        uri = uri.substring(context.length());

        if(uri.indexOf("/css/") == 0) {
            chain.doFilter(request, response);
        } else if (uri.indexOf("/js/") == 0) {
            chain.doFilter(request, response);
        } else if (uri.indexOf("/auth/") == 0) {
            chain.doFilter(request, response);
        } else if (uri.indexOf("/admin/") == 0) {
            chain.doFilter(request, response);
        } else if (uri.indexOf("/register") == 0) {
            chain.doFilter(request, response);
        } else if (uri.indexOf("/disconnect") == 0) {
            chain.doFilter(request, response);
        } else if (uri.indexOf("/connexion") == 0) {
            chain.doFilter(request, response);
        } else if (uri.indexOf("/validation") == 0) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/validation.jsp");
            dispatcher.forward(request, response);
        } else if (uri.compareTo("/") == 0 || uri.compareTo("/auth/") == 0) {
            response.sendRedirect(request.getContextPath() + "/auth/index");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/404.jsp");
            dispatcher.forward(request, response);
        }

    }

    public void init(FilterConfig config) {
    }

}
