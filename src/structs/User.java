
@TableName(name = "users")
@PrimaryKey(value = "PRIMARY KEY (id)")
public class User {


    @FieldType(name="login", type ="TEXT NOT NULL UNIQUE")
    String login="NULL";

    @FieldType(name="password", type ="TEXT NOT NULL")
    String password="NULL";
    @FieldType(type = "SERIAL ",name="id")
    protected Integer id;

}
