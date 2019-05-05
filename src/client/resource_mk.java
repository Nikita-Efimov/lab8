import java.util.ListResourceBundle;

public class resource_mk extends ListResourceBundle {
    private final static Object[][] contents = { { "close", "затвори" }, { "submit", "во ред" }, { "remove", "отстрани" },
            { "remove_first", "избришете прво" }, { "show", "да се покаже" }, { "add", "додадете" },
            { "add_if_max", "додадете ако" }, { "log in", "влезете" }, { "sign in", "да се регистрирате" },
            { "commands", "тимови" }, { "help", "помош" }, { "authorization", "овластување" }, { "login", "најавите" },
            { "password", "лозинка" }, { "laguages", "промена на јазикот" }, { "name", "името" }, { "size", "големината" } };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
