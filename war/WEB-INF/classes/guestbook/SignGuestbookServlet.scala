package guestbook

import _____________lolz____________.PMFLolz
import _root_.com.google.appengine.api.users.{UserService, User, UserServiceFactory}
import _root_.javax.jdo.PersistenceManager
import java.util.Date
import _root_.javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import java.util.logging.Logger

class SignGuestbookServlet extends HttpServlet {
  override def doPost(req : HttpServletRequest, resp: HttpServletResponse) {
        val userService = UserServiceFactory.getUserService();
        val user = userService.getCurrentUser();

        val content = req.getParameter("content");
        val date = new Date();
        val greeting = new Greeting(user, content, date);

        val pm = PMF.pmfInstance.getPersistenceManager
        try {
            pm.makePersistent(greeting);
        } finally {
            pm.close();
        }

        resp.sendRedirect("/guestbook");
  }
}