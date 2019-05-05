import java.util.ListResourceBundle;

public class resource_ru extends ListResourceBundle {
    private final static Object[][] contents = { { "close", "закрыть" }, { "submit", "ок" }, { "remove", "удалить" },
            { "remove_first", "удалить первый" }, { "show", "показать" }, { "add", "добавить" },
            { "add_if_max", "добавить если" }, { "log in", "войти" }, { "sign in", "зарегистрироваться" },
            { "commands", "команды" }, { "help", "помощь" }, { "authorization", "авторизация" }, { "login", "логин" },
            { "password", "пароль" }, { "laguages", "сменить язык" }, { "name", "имя" }, { "size", "размер" } };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
