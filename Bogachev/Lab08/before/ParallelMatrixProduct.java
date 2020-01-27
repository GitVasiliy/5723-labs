public class ParallelMatrixProduct extends Thread
{
	private int countThread;
	private UsualMatrix matrix1;
	private UsualMatrix matrix2;
	private int s;
	private int p;
	private UsualMatrix res;

	public ParallelMatrixProduct (int cT,int p, UsualMatrix m1, UsualMatrix m2, int s, UsualMatrix res)
	{
		this.countThread = cT;
		this.matrix1 = m1;
		this.matrix2 = m2;
		this.s = s;
		this.p = p;
		this.res = res;
	}

	public void run () 
	{
		for (int i = 0; i < matrix1.getRow (); i++)
		{
			for (int j = s; j < p; j++)
			{
				int sum = 0;
				for (int k = 0; k < matrix1.getRow (); k++)
				{
					sum += matrix1.getElement (i, k) * matrix2.getElement(k, j);
				}
				res.setElement (i, j , sum);
			}
		}
	}
}