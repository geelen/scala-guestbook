package guestbook

import _root_.com.google.appengine.api.users.{UserService, User, UserServiceFactory}
import _root_.javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

class GuestbookServlet extends HttpServlet {
    override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val userService = UserServiceFactory.getUserService();
        val user = userService.getCurrentUser();

        if (user != null) {
            resp.setContentType("text/plain");
            resp.getWriter().println("HelloHOLYSHITITSSCALA, " + user.getNickname());
//            resp.getWriter().println(userService.createLogoutURL(req.getRequestURI()));
        } else {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
    }
}