package Controller.Servlets;

import Model.Forums;
import Model.Messages;
import Model.Subscriptions;
import Model.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Forum", urlPatterns = {"/auth/forum"})
public class Forum extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("id") == null) {
            HttpSession session = request.getSession();
            Users U = (Users)session.getAttribute("user");

            List<Forums> forumsList = Forums.getList();
            List<Forums> subscriptions = Subscriptions.getSubscriptions(U.getLogin());

            request.setAttribute("forums", forumsList);
            request.setAttribute("subscriptions", subscriptions);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/forumlist.jsp");
            dispatcher.forward(request, response);
        } else {
            int id;

            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (IllegalArgumentException e) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/forumlist.jsp");
                dispatcher.forward(request, response);
                return;
            }

            Forums F = Forums.getForum(id);
            if(F == null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/404.jsp");
                dispatcher.forward(request, response);
                return;
            }

            List<Messages> messagesList = Messages.getMessages(id);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/forum.jsp");
            request.setAttribute("forum", F);
            request.setAttribute("messages", messagesList);
            dispatcher.forward(request, response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
