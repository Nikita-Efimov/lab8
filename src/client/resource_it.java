import java.util.ListResourceBundle;

public class resource_it extends ListResourceBundle {
    private final static Object[][] contents = { { "close", "spegni" }, { "submit", "va bene" }, { "remove", "rimuovere" },
            { "remove_first", "cancella prima" }, { "show", "mostrare" }, { "add", "aggiungere" },
            { "add_if_max", "aggiungi if" }, { "log in", "accedi" }, { "sign in", "registrarsi" },
            { "commands", "comandi" }, { "help", "aiutare" }, { "authorization", "autorizzazione" }, { "login", "accesso" },
            { "password", "password" }, { "laguages", "cambia lingua" }, { "name", "il nome" }, { "size", "dimensione" } };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
