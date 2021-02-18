package Controller.Servlets;

import Model.FactoryInstance;
import Model.Forums;
import Model.Subscriptions;
import Model.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Index", urlPatterns = {"/auth/index"}, loadOnStartup = 1)
public class Index extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users logged = (Users)session.getAttribute("user");
        List<Forums> subscriptions = Subscriptions.getSubscriptions(logged.getLogin());
        request.setAttribute("subs", subscriptions);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        FactoryInstance.init();
    }
}
