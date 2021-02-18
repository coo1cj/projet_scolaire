package Controller.Servlets;

import Model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "UserList", urlPatterns = {"/admin/userlist", "/admin/deleteuser"})
public class UserList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users U = Users.getUser(request.getParameter("user"));
        String uri = request.getRequestURI();
        uri = uri.substring(request.getContextPath().length());

        if(uri.compareTo("/admin/deleteuser") == 0) {
            if(U == null) {
                response.sendRedirect(request.getContextPath() + "/admin/userlist?failed");
                return;
            }

            U.delete();
            response.sendRedirect(request.getContextPath() + "/admin/userlist");
            return;

        }

        response.setContentType("text/plain");
        if(U == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().flush();
            return;
        }

        int level;
        try {
            level = Integer.parseInt(request.getParameter("level"));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().flush();
            return;
        }

        U.setRole((byte) level);
        if(U.merge()) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.getWriter().flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Users> usersList = Users.getUsers();
        request.setAttribute("userlist", usersList);
        request.getRequestDispatcher("/jsp/userlist.jsp").forward(request, response);
    }
}
