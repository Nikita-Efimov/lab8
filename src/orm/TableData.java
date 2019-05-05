import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableData  implements DBorm {

    private Connection dbConnection;

    private Class thisclass;
    private String name;
    private String primary;
    private Map<String, String> attributes = new LinkedHashMap<>();

    public TableData(String name,String primary,Connection dbConnection,Class thisclass) {
        this.name = name;
        this.primary = primary;
        this.dbConnection=dbConnection;
        this.thisclass=thisclass;
    }

    public String getSQLAttributes  () {
        StringBuilder builder = new StringBuilder(" (");
        builder.append(attributes.keySet().toArray()[0]);
        for (int i = 1; i < attributes.size(); i++) {
            builder.append(", " + attributes.keySet().toArray()[i]);
        }
        builder.append(")");
        return builder.toString();
    }


    public String getSQLAttributesWithoutId  () {
        StringBuilder builder = new StringBuilder(" (");
        builder.append(attributes.keySet().toArray()[0]);
        Object[] ks=attributes.keySet().toArray();
        for (int i = 1; i < attributes.size(); i++) {
            if(!ks[i].equals("id"))
            builder.append(", " + ks[i]);
        }
        builder.append(")");
        return builder.toString();
    }




    /*Getters, setters*/
    public String getName() {
        return name;
    }


    public String getPrimary() {
        return primary;
    }



    public void setName(String name) {
        this.name = name;
    }
    public Map<String, String> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }




    public List getObjectsFromTable() {
        Statement statement = null;
        List<Object> list=new LinkedList();

        try {
            statement = dbConnection.createStatement();
            Field[] fields = thisclass.getDeclaredFields();
            TableData tableData = this;

            StringBuilder build = new StringBuilder("SELECT * FROM " + tableData.name );
            ResultSet result = statement.executeQuery(build.toString());
            Object curobj=null;




            while (result.next()){
                try {
                    curobj= thisclass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(FieldType.class)) {

                        try{
                            field.set(curobj,result.getObject(field.getAnnotation(FieldType.class).name()));

                        }
                        catch (IllegalArgumentException e){

                            if(field.getAnnotation(FieldType.class).name().equals("init_date")){


                                field.set(curobj,OffsetDateTime.ofInstant( Instant.ofEpochMilli(((Timestamp)result.getObject(field.getAnnotation(FieldType.class).name())).getTime()), ZoneId.systemDefault()));

                            }


                        }


                    }
                }
                list.add(curobj);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return list;
    }




    public void addObjectToTable(Object object) {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
            Field[] fields = object.getClass().getDeclaredFields();
            TableData tableData = this;
            if(!  ((TableName) object.getClass().getAnnotation(TableName.class)).name().equals(this.name)){

                throw new RuntimeException("uncorrect object");


            }
            StringBuilder build = new StringBuilder("INSERT INTO " + ((TableName) object.getClass().getAnnotation(TableName.class)).name() + tableData.getSQLAttributesWithoutId() + " VALUES(");
            boolean first = true;
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(FieldType.class)) {
                    try {
                        if (!first) build.append(",'" + field.get(object).toString() + "'");
                        else {
                            first = false;
                            build.append("'" + field.get(object).toString() + "'");
                        }
                    }
                    catch (NullPointerException e){


                    }

                }
            }
            build.append(" ) returning \"id\" ;");
           //System.out.println(build.toString());
            ResultSet rs= statement.executeQuery(build.toString());



            int generatedKey = 0;

            if (rs.next()) {
                generatedKey = rs.getInt("id");
                thisclass.getDeclaredField("id").set(object,generatedKey);


            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }






        } catch (SQLException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    /**
     * обновить объект
     * @param objOld  Старый объяект который нужно обновить
     * @param attr   Атрибут который будем обновлять
     * @param newData Новая data
     * @param attrOfCon Атрибут критерий, по которому будет сравнение
     */
    public void updateObjectInTable(Object objOld, String attr, String newData, String attrOfCon) {
        Statement statement = null;
        try {



            if(!  ((TableName) objOld.getClass().getAnnotation(TableName.class)).name().equals(this.name)){

                throw new RuntimeException("uncorrect object");


            }



            statement = dbConnection.createStatement();
            Field[] fields = objOld.getClass().getDeclaredFields();
            Field f = null;
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if(fields[i].getAnnotation(FieldType.class).name().equals(attrOfCon)) {
                    f = fields[i];
                    break;
                }
            }
            if (!f.getAnnotation(FieldType.class).type().equals("INTEGER")) newData = "'"+newData+"'";
            TableData tableData = this;
            StringBuilder build = new StringBuilder("UPDATE " + tableData.getName() + " SET " + attr + " = " + newData + " WHERE " + attrOfCon + "='" + f.get(objOld).toString() + "';");
            statement.execute(build.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param object
     * @param attrOfCon  атрибут по значению которого будет удален объект
     */
    public void deleteObjectInTable(Object object, String attrOfCon) {
        Statement statement = null;
        try {

            if(!  ((TableName) object.getClass().getAnnotation(TableName.class)).name().equals(this.name)){

                throw new RuntimeException("uncorrect object");


            }


            statement = dbConnection.createStatement();
            Field[] fields = object.getClass().getDeclaredFields();
            Field f = null;
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if(fields[i].getAnnotation(FieldType.class).name().equals(attrOfCon)) {
                    f = fields[i];
                    break;
                }
            }
            TableData tableData = this;
            StringBuilder build = new StringBuilder("DELETE FROM " + tableData.getName() + " WHERE " + attrOfCon + "='" + f.get(object).toString() + "';");
            statement.execute(build.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void addORM(City city) {

        this.addObjectToTable(city);

    }

    @Override
    public void removeFirstORM() {

    }

    @Override
    public void removeORM(City city) {
        this.deleteObjectInTable(city,"id");
    }

    @Override
    public String showORM() {


        List<Object> list=this.getObjectsFromTable();
        return  list.stream().map(Object::toString)
                .collect(Collectors.joining("\n"));


    }

    @Override
    public boolean addIfMaxORM(City city) {
        return false;
    }

    @Override
    public boolean removeLowerORM(City city) {
        return false;
    }
}
