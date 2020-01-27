import java.util.Random;

public class ResourcePool extends Thread{
	private int count; 
	private int numberPC;
	private Do l;
	private Calendar1 c;

	public ResourcePool(int count, int i, Do k, Calendar1 c)
	{
		this.count = count;
		this.numberPC = i;
		this.l = k;
		this.c = c;
	}


	public void run(){
		int minute = c.getMinute();
		int hour = c.getHour();
		Random rand = new Random();
		int m = rand.nextInt(10500);
		m += 1500;
		long r = (long) m;
		this.numberPC += 1;
		long t1 = System.currentTimeMillis();
		System.out.println("Current time - " + hour + ":" + minute + " - User " + this.count + " sat down at the PC" + this.numberPC + " - " + m + "m");
		try{
			Thread.sleep(r);
		}
		catch (InterruptedException e){}
		long t2 = System.currentTimeMillis();
		l.set(false, numberPC - 1);
		c.add(m);
		minute = c.getMinute();
		hour = c.getHour();
		System.out.println("Current time - " + hour + ":" + minute + " - User " + this.count + " got up from the PC" + this.numberPC + " - " + m + "m " + "System time: " + (t2 - t1));
	}
}