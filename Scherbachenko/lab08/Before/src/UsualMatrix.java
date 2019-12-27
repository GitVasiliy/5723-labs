import java.io.IOException;
import java.util.Random;

public class UsualMatrix
{
	private int row;
	private int column;
	private int [][]matrix;

	public UsualMatrix () {}

	public UsualMatrix (int r, int c)
	{
		this.row = r;
		this.column = c;
		this.matrix = new int [row][column];
		Random random = new Random (0);
		for (int i = 0; i < this.row; i++)
		{
			for (int j = 0; j < this.column; j++)
			{
				matrix[i][j] = random.nextInt (10);
				matrix[i][j] += 1;
			}
		}
	}

	public void setElement (int i, int j, int value)
	{
		this.matrix[i][j] = value;
	}

	public int getElement (int i, int j)
	{
		return this.matrix[i][j];
	}

	public int getRow ()
	{
		return this.row;
	}

	public int getColumn ()
	{
		return this.column;
	}

	public UsualMatrix product (UsualMatrix m)
	{
		UsualMatrix res = new UsualMatrix (this.row, m.column);
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < m.column; j++)
			{
				for (int k = 0; k < column; k++)
				{
					res.matrix[i][j] += this.matrix[i][k] * m.matrix[k][j];
				}
			}

		}
		return res;
	}

	public UsualMatrix productParallel (int thread_count, UsualMatrix m, int[] mas) throws InterruptedException
	{
		UsualMatrix res = new UsualMatrix (this.row, this.column);
		int s = 0;
		int tmp = mas[0];
		ParallelMatrixProduct []thread = new ParallelMatrixProduct [thread_count];
		for (int i = 0; i < thread_count; i++)
		{
			thread[i] = new ParallelMatrixProduct (thread_count, tmp, this, m, s, res);
			thread[i].start ();
			if (i < thread_count - 1) tmp += mas[i + 1];
			s += mas[i];
		}
		for (ParallelMatrixProduct parallelMatrixProduct : thread)
		{
			parallelMatrixProduct.join ();
		}
		System.out.print ("\n");
		return res;
	}

	public int[] potok (int thread_count, int column){
		int j = 0;
		int []mas = new int[thread_count];
		for (int i = 0; i < this.column; i++){
			mas[j]++;
			j++;
			if (j == thread_count) j = 0;
		}
		return mas;
	}

	public String toString ()
	{
		StringBuilder c = new StringBuilder ();
		for (int i = 0; i < this.row; i++)
		{
			for (int j = 0; j < this.column; j++)
			{
				c.append ("[");
				c.append (this.matrix[i][j]);
				c.append ("]");
			}
			c.append ("\n");
		}
		return c.toString ();
	}
}