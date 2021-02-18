package Controller.Servlets;

import Model.Forums;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteForum", urlPatterns = {"/admin/delete"})
public class DeleteForum extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/forum");
        }
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/auth/forum");
            return;
        }

        Forums F = Forums.getForum(id);
        if(F != null) {
            F.delete();
        }
        response.sendRedirect(request.getContextPath() + "/auth/forum");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
