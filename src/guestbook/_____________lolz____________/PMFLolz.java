package guestbook._____________lolz____________;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMFLolz {
    private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMFLolz() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}