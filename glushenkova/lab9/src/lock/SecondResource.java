package lock;

public class SecondResource
{
	FirstResource r;

	public synchronized int get()
	{
		return r.returnR();
	}
	public synchronized int returnR()
	{
		return 2;
	}
	public void setR(FirstResource a)
	{
		this.r = a;
	}
}