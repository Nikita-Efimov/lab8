import java.util.ListResourceBundle;

public class resource_en extends ListResourceBundle {
    private final static Object[][] contents={

            {"close","close"},
            {"submit","submit"},
            {"remove","remove"},
            {"remove_first","remove_first"},
            {"show","show"},
            {"add","add"},
            {"add_if_max","add_if_max"}


    } ;


    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
