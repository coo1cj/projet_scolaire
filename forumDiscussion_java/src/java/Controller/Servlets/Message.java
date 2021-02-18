package Controller.Servlets;

import Model.Messages;
import Model.MessagesPK;
import Model.Users;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "Message", urlPatterns = {"/auth/sendmessage", "/auth/edit", "/auth/delete"})
public class Message extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String forum = request.getParameter("forum");
        String id = request.getParameter("id");

        if(forum == null) {
            response.sendRedirect(request.getContextPath() +  "/auth/forum");
            return;
        }

        Users U = (Users)request.getSession().getAttribute("user");

        String uri = request.getRequestURI();
        uri = uri.substring(request.getContextPath().length());

        if(uri.compareTo("/auth/delete") == 0) { // delete
            Messages M = getMessage(request);
            if(M == null || M.getUser() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().flush();
                return;
            }

            if(U.getLogin().compareTo(M.getUser()) == 0) {
                M.delete();
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().flush();
                return;
            }
        }


        if(id != null) { // Modification
            response.setContentType("text/plain");
            Messages M = getMessage(request);
            if(M == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().flush();
                return;
            }

            M.setContent(request.getParameter("content"));
            M.setTime(new Timestamp(System.currentTimeMillis()));

            if(M.getUser() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            if(M.getUser().compareTo(U.getLogin()) != 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else if(M.merge()) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            response.getWriter().flush();

        } else { // Ajout

            Messages M = createMessage(request);
            if(M == null || M.getContent() == null || M.getContent().length() == 0) {
                response.sendRedirect(request.getContextPath() + "/auth/forum?id=" + forum + "&failed");
                return;
            }

            if (M.persist()) {
                response.sendRedirect(request.getContextPath() + "/auth/forum?id=" + forum);
            } else {
                response.sendRedirect(request.getContextPath() + "/auth/forum?id=" + forum + "&failed");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() +  "/auth/forum");
    }

    private Messages getMessage(HttpServletRequest request) {
        int id, forum;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            forum = Integer.parseInt(request.getParameter("forum"));
        } catch (IllegalArgumentException e) {
            return null;
        }

        MessagesPK pk = new MessagesPK();
        pk.setId(id);
        pk.setForum(forum);

        return Messages.get(pk);
    }

    private Messages createMessage(HttpServletRequest request) {
        Messages M = new Messages();
        int forum;
        try {
            forum = Integer.parseInt(request.getParameter("forum"));
        } catch (IllegalArgumentException e) {
            return null;
        }

        Users U = (Users)request.getSession().getAttribute("user");

        M.setForum(forum);
        M.setUser(U.getLogin());
        M.setContent(request.getParameter("content"));
        if(M.getContent() == null) {
            return null;
        }
        M.setTime(new Timestamp(System.currentTimeMillis()));
        return M;
    }

}
