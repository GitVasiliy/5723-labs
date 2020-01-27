import java.util.Calendar;

public class Calendar1{
	private Calendar c = Calendar.getInstance();

	public int getMinute(){
		return c.get(Calendar.MINUTE);
	}

	public int getHour(){
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public void add(int value){
		c.add(Calendar.MINUTE, value);
	}
}