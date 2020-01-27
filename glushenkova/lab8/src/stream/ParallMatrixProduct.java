package stream;

public class ParallMatrixProduct
{
	private MyThread[] threads;
	//private int start;
	private UsualMatrix res;

	private class MyThread extends Thread
	{
		private UsualMatrix oneMatrix;
		private UsualMatrix twoMatrix;
		private UsualMatrix res;
		private int start;
		private int end;

		MyThread(UsualMatrix a, UsualMatrix b, UsualMatrix res, int s, int e)
		{
			this.oneMatrix = a;
			this.twoMatrix = b;
			this.res = res;
			this.start = s;
			this.end = e;
		}
		@Override
		public void run()
		{
			System.out.println(Thread.currentThread().getName() + " " + start + " " + end);
			//System.out.println(start + " " + end + "\n"); // закомментировать принт
			/*for (int i = start; i < end; i++)
			{
				for (int j = 0; j < twoMatrix.getRow(); j++)
				{
					System.out.print(this.oneMatrix.getElement(i, j) + " ");
				}
				System.out.print("\n");
			}
			System.out.print("\n");
			for (int i = 0; i < twoMatrix.getRow(); i++)
			{
				for (int j = 0; j < twoMatrix.getColumn(); j++)
				{
					System.out.print(this.twoMatrix.getElement(i, j) + " ");
				}
				System.out.print("\n");
			}
			System.out.print("\n");*/


			for (int i = start; i < end; i++)
			{
				for (int j = 0; j < twoMatrix.getColumn(); j++)
				{
					int value = 0;
					for (int k = 0; k < twoMatrix.getRow(); k++)
					{
						value = value + this.oneMatrix.getElement(i, k) * this.twoMatrix.getElement(k, j);
					}	
					//System.out.print(value + " ");
					res.setElement(i, j, value);
				}
				//System.out.print("\n");
			}
		}
		private UsualMatrix getRes()
		{
			return this.res;
		}
	}

	ParallMatrixProduct(){}
	ParallMatrixProduct(int num)
	{
		this.threads = new MyThread[num];
		//start = 0;
	}

	public UsualMatrix mul(UsualMatrix a, UsualMatrix b) throws Exception
	{
		if (a.getColumn() != b.getRow() || b.getColumn() != a.getRow()) throw new Exception("errorMul");
		//this.res = new UsualMatrix(a.getRow(), b.getColumn());
		int s = 0;
		int e = 0;
		if (b.getRow() % threads.length != 0) 
			e = (int)(b.getRow()/threads.length);
		else
			e = (int)(b.getRow()/threads.length);
		boolean isEnd = false;
		int c = 0;
		UsualMatrix res = new UsualMatrix(a.getRow(), b.getColumn());
		for (int i = 0; i < threads.length; i++)
		{
			if (i == 0) c = a.getRow()/threads.length + a.getRow() % threads.length;
			else c = (a.getRow()/threads.length) + 1;
			threads[i] = new MyThread(a, b, res, s, s + c);
			res = threads[i].getRes();
			threads[i].start();
			s += c - 1;
		}
		for (int i = 0; i < threads.length; i++)
		{
			try
			{
				threads[i].join();
			}
			catch(Exception exp)
			{
				System.out.println(exp.getMessage());
			}
		}
		System.out.print("\n");
		return res;
		//System.out.println(res);
	}
}
/*
			try
			{
				threads[i].join();
			}
			catch(Exception exp)
			{
				System.out.println(exp.getMessage());
			}
			*/