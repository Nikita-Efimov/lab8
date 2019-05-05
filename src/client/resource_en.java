import java.util.ListResourceBundle;

public class resource_en extends ListResourceBundle {
    private final static Object[][] contents={
            {"close","close"},
            {"submit","submit"},
            {"remove","remove"},
            {"remove_first","remove_first"},
            {"show","show"},
            {"add","add"},
            {"add_if_max","add_if_max"},
            {"log in","log in"},
            {"sign in","sign in"},
            {"commands","commands"},
            {"help","help"},
            {"authorization","authorization"},
            {"login","login"},
            {"password","password"},
            {"laguages","laguages"},
            {"name","name"},
            {"size","size"}
    } ;

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
