package lock;

public class FirstResource
{
	SecondResource r;

	public synchronized int get()
	{
		return r.returnR();
	}
	public synchronized int returnR()
	{
		return 1;
	}
	public void setR(SecondResource a)
	{
		this.r = a;
	}
}