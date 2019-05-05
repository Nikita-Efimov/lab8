
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


@PrimaryKey(value = "PRIMARY KEY (id, parent_id) ")
@TableName(name="objects")
class City extends Place implements Comparable, Serializable {

	@FieldType(type = "text",name="name")
	protected String name = "Без имени";
	@FieldType(type = "int4",name="area_size")
	protected Integer areaSize;
	@FieldType(type = "int4",name="x")
	protected Integer x;
	@FieldType(type = "int4",name="y")
	protected Integer y;
	@FieldType(type = "timestamp",name="init_date")
	protected OffsetDateTime initDate;

	@FieldType(type = "SERIAL ",name="id")
	protected Integer id;
	@FieldType(type = "int4 REFERENCES users(id) ON DELETE CASCADE ",name="parent_id")
	protected Integer parent_id;



	public City() {
		this("Unnamed", 0);
	}

	public City(String name, Integer areaSize) {
		this(name, areaSize, 0, 0);
	}

	public City(String name, Integer areaSize, Integer x, Integer y) {
		super(name);
		initDate = OffsetDateTime.now();
		this.areaSize = areaSize;
		this.x = x;
		this.y = y;
	}

	public City(String name, Integer areaSize, Integer x, Integer y, Integer initDate) {
		super(name);
		this.initDate = OffsetDateTime.now();
		this.areaSize = areaSize;
		this.x = x;
		this.y = y;
	}

	public int getAreaSize() {
		return areaSize;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public long getInitDate() {
		return (long)initDate.toEpochSecond();
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Object object) {
		if (this == object)
            return 0;

		if (object == null)
			throw new NullPointerException();

		if (this.getClass() != object.getClass())
			throw new ClassCastException();

		City city = (City)object;
		return this.areaSize.compareTo(city.areaSize) + this.name.compareTo(city.name);
	}

	@Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null)
            return false;

        if (this.getClass() != object.getClass())
            return false;

        City city = (City)object;
        if (!this.name.equals(city.name) || !this.areaSize.equals(city.areaSize))
            return false;

        return true;
    }

    @Override
	public int hashCode() {
		return Arrays.deepHashCode((Object[])new Object[]{name, areaSize});
	}

    @Override
    public String toString() {
        return id+" Имя города: " + name + "\nПлощадь: " + areaSize + "\nКоординаты: " + x + ", " + y + "\nДата создания объекта: " + initDate.format(DateTimeFormatter.ofPattern("cccc, dd MMMM, yyyy kk:mm X"));
    }









}
