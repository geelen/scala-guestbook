<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="guestbook.Greeting" %>
<%@ page import="guestbook._____________lolz____________.PMFLolz" %>
<%@ page import="org.apache.taglibs.standard.functions.Functions" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>Hello, <%= user.getNickname() %>! (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
} else {
%>
<p>Hello!
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
    to include your name with greetings you post.</p>
<%
    }
%>

<%
    PersistenceManager pm = PMFLolz.get().getPersistenceManager();
    String query = "select from " + Greeting.class.getName() + " order by date desc";
    List<Greeting> greetings = (List<Greeting>) pm.newQuery(query).execute();
    if (greetings.isEmpty()) {
%>
<p>The guestbook has no messages.</p>
<%
} else {
    for (Greeting g : greetings) {
        if (g.getAuthor() == null) {
%>
<p>An anonymous person wrote:</p>
<%
} else {
%>
<p><b><%= g.getAuthor().getNickname() %>
</b> wrote:</p>
<%
    }
%>
<blockquote><%= Functions.escapeXml(g.getContent()) %></blockquote>
<%
        }
    }
    pm.close();
%>

<form action="/sign" method="post">
    <div><textarea name="content" rows="3" cols="60"></textarea></div>
    <div><input type="submit" value="Post Greeting"/></div>
</form>

</body>
</html>