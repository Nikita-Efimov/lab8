import java.util.ListResourceBundle;

public class resource_es extends ListResourceBundle {
    private final static Object[][] contents = { { "close", "apagar" }, { "submit", "ok" }, { "remove", "quitar" },
            { "remove_first", "borrar primero" }, { "show", "para mostrar" }, { "add", "añadir" },
            { "add_if_max", "agregar si" }, { "log in", "iniciar sesión" }, { "sign in", "para registrarse" },
            { "commands", "equipos" }, { "help", "ayuda" }, { "authorization", "autorización" }, { "login", "iniciar sesión" },
            { "password", "contraseña" }, { "laguages", "cambiar idioma" }, { "name", "el nombre" }, { "size", "el tamaño" } };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
