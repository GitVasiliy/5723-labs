package lock;

public class FirstMyThread extends Thread
{
	FirstResource r;

	FirstMyThread()
	{
	}
	FirstMyThread(FirstResource a)
	{
		this.r = a;
	}
	@Override
	public void run()
	{
		System.out.println(r.get());
	}
}