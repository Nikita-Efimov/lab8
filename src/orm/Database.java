import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static final String DB_DRIVER = "org.postgresql.Driver";

    //  private static final String DB_PASSWORD = "admin";
    //  private static final String DB_USER = "postgres";
    //  private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/laba7DB";

    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/lab7";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    public Connection dbConnection;



    public Database(){

        getDBConnection();

        createTableOfClass(User.class,false);
        createTableOfClass(City.class,false);


    }



    public Map<String, TableData> tables = new HashMap<>();

    private void getDBConnection() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void activateQuery(final String q) throws SQLException {
        Statement statement;
        statement = dbConnection.createStatement();
        statement.execute(q);
    }





    public void createTableOfClass(Class objectClass, boolean hardReset) {
        Statement statement = null;
        StringBuilder build=null;
        try {
            statement = dbConnection.createStatement();
            TableData tableData = new TableData(((TableName) objectClass.getAnnotation(TableName.class)).name(),((PrimaryKey) objectClass.getAnnotation(PrimaryKey.class)).value(),dbConnection,objectClass);
            if (hardReset) statement.execute("DROP TABLE " + tableData.getName() + " CASCADE;");
            Field[] fields = objectClass.getDeclaredFields();
             build = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableData.getName() );
            boolean first=true;
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(FieldType.class)) {
                    tableData.getAttributes().put(field.getAnnotation(FieldType.class).name(), field.getAnnotation(FieldType.class).type());
                    if(!first){
                        build.append(", " + field.getAnnotation(FieldType.class).name() + " " + field.getAnnotation(FieldType.class).type());

                    }else{
                        build.append("( " + field.getAnnotation(FieldType.class).name() + " " + field.getAnnotation(FieldType.class).type());
                        first=false;
                    }
                }
            }
            tables.put(tableData.getName(), tableData);
            build.append(", "+tableData.getPrimary()+" );");

            //System.out.println(build.toString());

            statement.execute(build.toString());

        } catch (SQLException e) {
//            System.out.println(build.toString());
            e.printStackTrace();
        }
    }






}
