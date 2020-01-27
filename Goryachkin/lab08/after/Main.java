public class Main 
{
	public static void main(String[] args)
	{
		Integer[] array = new Integer[10000];
		for (int i = 0; i < 10000; i++)
		{
			array[i] = Integer.valueOf((int)(Math.random() * 12345)%127);
			//System.out.println(array[i]);
		}
		MergeSort p = new MergeSort(array, 1);
		MergeSort s = new MergeSort(array, 4);
		long timeBeginUsual = System.currentTimeMillis();
		p.sort();
		long timeEndUsual = System.currentTimeMillis();
		long timeBeginThread = System.currentTimeMillis();
		s.sort();
		long timeEndThread = System.currentTimeMillis();
		//System.out.println(s);
		System.out.println("Usual multiply: " + (timeEndUsual - timeBeginUsual) + "ms" + "\n");
		System.out.println("Thread multiply: " + (timeEndThread - timeBeginThread) + "ms" + "\n");	
	}
}