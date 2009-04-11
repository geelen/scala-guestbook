package guestbook

import _root_.javax.jdo.JDOHelper

object PMF {
  val pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
}