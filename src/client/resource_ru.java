import java.util.ListResourceBundle;

public class resource_ru extends ListResourceBundle {
    private final static Object[][] contents={

            {"close","закрыть"},
            {"submit","ок"},
            {"remove","удалить"},
            {"remove_first","удалить первый"},
            {"show","показать"},
            {"add","добавить"},
            {"add_if_max","добавить если чото"}



    } ;


    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
