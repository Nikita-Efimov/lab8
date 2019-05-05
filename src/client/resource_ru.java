import java.util.ListResourceBundle;

public class resource_ru extends ListResourceBundle {
    private final static Object[][] contents={

            {"close","закрыть"},
            {"submit","ок"},
            {"remove","удалить"},
            {"remove_first","удалить первый"},
            {"show","показать"},
            {"add","добавить"},
            {"add_if_max","добавить если чото"},
            {"log in","Войти"},
            {"sign in","Зарегистрироваться"},
            {"commands","команды"},
            {"help","помощь"},
            {"authorization","авторизация"},
            {"laguages","сменить язык"}



    } ;


    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
