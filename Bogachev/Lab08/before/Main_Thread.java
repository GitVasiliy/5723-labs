import java.io.IOException;

public class Main_Thread
{
	public static void main (String[] args) throws InterruptedException
	{
		UsualMatrix m1 = new UsualMatrix (100, 100);
		UsualMatrix m2 = new UsualMatrix (100, 100);
		UsualMatrix tmp = new UsualMatrix (100, 100);
		UsualMatrix res = new UsualMatrix (100, 100);
		long time1 = System.currentTimeMillis ();
		tmp = m1.product (m2);
		//System.out.println (tmp);
		long time2 = System.currentTimeMillis ();
		m1.productParallel (10, m2, res);
		//System.out.println (res);
		long time3 = System.currentTimeMillis ();
		System.out.println (tmp.equals(res));
		System.out.print ("One thread counter: ");
		System.out.println (time2 - time1);
		System.out.print ("Many thread counter: ");
		System.out.println (time3 - time2);
	}
}

