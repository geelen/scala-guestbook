package guestbook

import _root_.com.google.appengine.api.users.{UserService, User, UserServiceFactory}
import _root_.javax.jdo.PersistenceManager
import java.util.Date
import _root_.javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import java.util.logging.Logger

//object SignGuestbookServlet {
//  Logger log = Logger.getLogger(SignGuestbookServlet.getClass.getName);
//}

class SignGuestbookServlet extends HttpServlet {
  override def doPost(req : HttpServletRequest, resp: HttpServletResponse) {
        val userService = UserServiceFactory.getUserService();
        val user = userService.getCurrentUser();

        val content = req.getParameter("content");
        val date = new Date();
        val greeting = new Greeting(user, content, date);

        val pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(greeting);
        } finally {
            pm.close();
        }

        resp.sendRedirect("/lol.jsp");
  }
}