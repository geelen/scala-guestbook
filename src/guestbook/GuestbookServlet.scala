package guestbook

import _root_.com.google.appengine.api.users.{UserService, User, UserServiceFactory}
import _root_.javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import _root_.scala.collection.jcl.{Buffer, Conversions}
import _root_.scala.xml.Elem
import java.util.logging.Logger
import org.apache.taglibs.standard.functions.Functions

object GuestbookServlet {
  val logger: Logger = Logger.getLogger(GuestbookServlet.getClass.getName)

  def greetingDescriptor(g: Greeting): Elem = {
    <span>{if (g.getAuthor == null) {
      <p>An anonymous person wrote:</p>
    } else {
      <p> <b>{g.getAuthor.getNickname}</b>wrote:</p>
    }}<blockquote>{g.getContent}</blockquote> </span>
  }

  def greetingsDescriptor(gs: List[Greeting]): List[Elem] = gs match {
    case List() => List(<p>The guestbook has no messages.</p>)
    case _ => gs.map(greetingDescriptor)
  }
}

class GuestbookServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val userService = UserServiceFactory.getUserService
    val user = userService.getCurrentUser
    resp.setContentType("text/html")
    val data: Elem =
    <html>
    <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    </head>
    <body> <div>{userLolz(user, userService, req)}</div> <div>{lols}</div>

    <form action="/sign" method="post">
    <div> <textarea name="content" rows="3" cols="60"> </textarea> </div>
    <div> <input type="submit" value="Post Greeting"/> </div>
    </form> </body>
    </html>
    resp.getWriter().println(data.toString)
  }

  def lols = {
    val pm = PMF.pmfInstance.getPersistenceManager
    val query = "select from guestbook.Greeting order by date desc range 0,10";
    val list = List.fromArray(StaticLolz.ffs(pm, query))
//    GuestbookServlet.logger.info(list.toString)
    val subdata = GuestbookServlet.greetingsDescriptor(list)
    pm.close
    subdata
  }

  def userLolz(user: User, userService: UserService, req: HttpServletRequest) =
    <p>{if (user != null) {
      <span>Hello, {user.getNickname()}! (You can&nbsp; <a href={userService.createLogoutURL(req.getRequestURI)}>sign out</a>.)</span>
    } else {
      <span>Hello!&nbsp;<a href={userService.createLoginURL(req.getRequestURI)}>Sign in</a>&nbsp;to include your name with greetings you post.</span>
    }}</p>
}
