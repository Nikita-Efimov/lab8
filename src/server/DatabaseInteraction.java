import java.lang.reflect.Field;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

interface DBUserInteractionable {
    public void addUser(final String login, final String password);
    public boolean isUserRegistred(final String login);
    public boolean auth(final String login, final String password);
    public Integer getUserIdFromLogin(final String login);
}

interface DBCityCollection {
    public void add(City city);
    public void removeFirst();
    public void remove(City city);
    public String show();
    public boolean addIfMax(City city);
    public boolean removeLower(City city);
}

class DatabaseInteraction implements DBUserInteractionable, DBCityCollection {
    private Connection dbConnection;
    private Database db;

    public DatabaseInteraction() {
            db=new Database();
            dbConnection=db.dbConnection;
    }

    private void activateQuery(final String q) throws SQLException {
        Statement statement;
        statement = dbConnection.createStatement();
        statement.execute(q);
    }

    @Override
    public void addUser(final String login, final String password) {
       User newuser =new User();
       newuser.login=login;
       newuser.password=password;

       db.tables.get( ((TableName)User.class.getAnnotation(TableName.class)).name()).addObjectToTable(newuser);


    }

    @Override
    public boolean isUserRegistred(final String login) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT id FROM users WHERE login = ?");
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            // Если что-то есть то да если нет то нет
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // заглушечка
        return true;
    }

    @Override
    public boolean auth(final String login, final String password) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT id FROM users WHERE login = ? and password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            // Если что-то есть то да если нет то нет
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // заглушечка
        return false;
    }

    @Override
    public Integer getUserIdFromLogin(final String login) {
        Integer id = null;

        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT id FROM users WHERE login = ?");
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();

            result.next();
            id = result.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException("this user not authorized");
        }

        return id;
    }

    @Override
    public void add(City city) {
        final int userId = getUserIdFromLogin(UserAuth.getCurrentThreadUserAuth().login);

      city.parent_id=userId;

        db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).addObjectToTable(city);

    }

    @Override
    public void removeFirst() {
        final int userId = getUserIdFromLogin(UserAuth.getCurrentThreadUserAuth().login);

      List<Object> list= db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).getObjectsFromTable();

        for (Object obj:list ) {
            City city=(City)obj;

            if(city.parent_id==userId){
                db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).removeORM(city);
                break;
            }


        }


    }

    @Override
    public void remove(City city) {
        final int userId = getUserIdFromLogin(UserAuth.getCurrentThreadUserAuth().login);
       if(city.parent_id==userId)
        db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).removeORM(city);



    }

    @Override
    public String show() {
        String returnString = "";

        returnString= db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).showORM();


        return returnString;
    }

    @Override
    public boolean addIfMax(City city) {
        final int userId = getUserIdFromLogin(UserAuth.getCurrentThreadUserAuth().login);

        List<Object> list= db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).getObjectsFromTable();



            City maxCity = new City();
            City curCity = new City();
            for (Object obj:list ){
                curCity=(City)obj;


                if (curCity.compareTo(maxCity) < 0) maxCity = curCity;
            }

            if (city.compareTo(maxCity) < 0) {
                db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).addObjectToTable(city);

                return true;
            }


        return false;
    }

    @Override
    public boolean removeLower(City city) {
        final int userId = getUserIdFromLogin(UserAuth.getCurrentThreadUserAuth().login);
        boolean isRemoved = false;

        List<Object> list= db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).getObjectsFromTable();







            City curCity = new City();
        for (Object obj:list ){
            curCity=(City)obj;


                if(city.compareTo(curCity) > 0 && city.parent_id==userId ) {
                    db.tables.get(((TableName)City.class.getAnnotation(TableName.class)).name()).removeORM(curCity);
                    isRemoved = true;
                }

            }


        return isRemoved;
    }





}
