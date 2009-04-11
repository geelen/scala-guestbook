package guestbook;

import javax.jdo.PersistenceManager;
import java.util.List;

public final class StaticLolz {
    public static Greeting[] ffs(PersistenceManager pm, String query) {
        List<Greeting> list = (List<Greeting>) pm.newQuery(query).execute();
        return list.toArray(new Greeting[list.size()]);
    }
}
