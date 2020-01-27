package lock;

public class SecondMyThread extends Thread
{
	SecondResource r;

	SecondMyThread()
	{
	}
	SecondMyThread(SecondResource a)
	{
		this.r = a;
	}
	@Override
	public void run()
	{
		System.out.println(r.get());
	}
}