package stream;

public class Test 
{
	public static void main(String[] args)
	{
		/*UsualMatrix a = new UsualMatrix(1000, 1000);
		UsualMatrix b = new UsualMatrix(1000, 1000);
		//System.out.println(a + "\n" + b);
		try 
		{
			long timeBeginUsual = System.currentTimeMillis();
			UsualMatrix res1 = a.product(b);
			long timeEndUsual = System.currentTimeMillis();
			//System.out.println(res1 + "\n");
			ParallMatrixProduct p = new ParallMatrixProduct(10);
			long timeBeginThread = System.currentTimeMillis();
			UsualMatrix res2 = p.mul(a, b);
			long timeEndThread = System.currentTimeMillis();
			//System.out.println(res2);
			System.out.println("Usual multiply: " + timeEndUsual + "-" + timeBeginUsual + "=" + (timeEndUsual - timeBeginUsual) + "\n");
			System.out.println("Thread multiply: " + timeEndThread + "-" + timeBeginThread + "=" + (timeEndThread - timeBeginThread) + "\n");
		}
		catch(Exception exp) 
		{
			System.out.println(exp.getMessage());
		}*/


		System.out.println("\n\nPart Two\n\n");
		//Integer[] array = {Integer.valueOf(12), Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(-2), Integer.valueOf(100)};
		Integer[] array = new Integer[1000];
		for (int i = 0; i < 1000; i++)
		{
			array[i] = Integer.valueOf((int)(Math.random() * 10));
		}
		MergeSort p = new MergeSort(array, 1);
		MergeSort s = new MergeSort(array, 4);
		long timeBeginUsual = System.currentTimeMillis();
		p.sort();
		long timeEndUsual = System.currentTimeMillis();
		long timeBeginThread = System.currentTimeMillis();
		s.sort();
		long timeEndThread = System.currentTimeMillis();
		//System.out.println(p);
		System.out.println("Usual multiply: " + timeEndUsual + "-" + timeBeginUsual + "=" + (timeEndUsual - timeBeginUsual) + "\n");
		System.out.println("Thread multiply: " + timeEndThread + "-" + timeBeginThread + "=" + (timeEndThread - timeBeginThread) + "\n");	
	}
}