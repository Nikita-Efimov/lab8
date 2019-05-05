

public class Mmain {


    public static void main(String[] args) {

        Database db=new Database();


        db.createTableOfClass(User.class,false);
        db.createTableOfClass(City.class,false);



        User user=new User();
        user.login="1233";
        db.tables.get("users").addObjectToTable(user);

        db.createTableOfClass(City.class,true);
        City city=new City();
        city.setName("name");
        city.areaSize=1233;
        city.x=10;
        city.parent_id=user.id;



        //System.out.println(city.toString());

        db.tables.get("objects").addObjectToTable(city);

        //System.out.println(db.tables.get("objects").getObjectsFromTable().get(0).toString());






    }



}
