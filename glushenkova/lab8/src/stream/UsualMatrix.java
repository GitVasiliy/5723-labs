package stream;
import java.lang.Math;
public class UsualMatrix
{
	protected int[][] matrix;
	protected int row;
	protected int column;

	public UsualMatrix()
	{}
	public UsualMatrix(int n, int m)
	{
		this.row = n;
		this.column = m;
		this.matrix = new int[n][m];
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				this.matrix[i][j] = (int)(Math.random() * 10);
			}
		}
	}

	public UsualMatrix sum(UsualMatrix a) throws Exception
	{
		if (this.getRow() != a.getRow() || this.getColumn() != a.getColumn())
		{
			throw new Exception("SumError");
		}
		UsualMatrix res = new UsualMatrix(this.getRow(), this.getColumn());
		for (int i = 0; i < this.getRow(); ++i)
		{
			for (int j = 0; j < this.getColumn(); ++j)
			{
				res.setElement(i, j, this.getElement(i, j) + a.getElement(i, j));
			}
		}
		return res;
	}

	public UsualMatrix product(UsualMatrix a) throws Exception
	{
		if (this.getRow() != a.getColumn() || this.getColumn() != a.getRow())
		{
			throw new Exception("ProductError");
		}
		UsualMatrix res = new UsualMatrix(this.getRow(), a.getColumn());
		for (int i = 0; i < this.getRow(); ++i)
    	{
    		for (int j = 0; j < a.getColumn(); ++j)
    		{
    			res.setElement(i, j, 0);
    			for (int k = 0; k < a.getRow(); ++k)
    			{
    				res.setElement(i, j, res.getElement(i, j) + this.getElement(i, k) * a.getElement(k, j));
    			}
    		}
    	}
    	return res;
	}

	final public int getRow()
	{
		return this.row;
	}

	final public int getColumn()
	{
		return this.column;
	}
	public void setRow(int r) 
    {
    	this.row = r;
    }
    public void setColumn(int c) 
    {
    	this.column = c;
    }
	public void setElement(int r, int c, int value) 
    {
    	this.matrix[r][c] = value;
    }
    public int getElement(int r, int c)
    {      
    	return this.matrix[r][c]; 
    }                               
    public boolean equals(UsualMatrix a)
    {
    	if (this.getClass() != a.getClass())
    	{
    		return false;
    	}
    	if (this.getRow() != a.getRow() || this.getColumn() != a.getColumn())
    	{
    		return false;
    	}
    	for (int i = 0; i < this.getRow(); ++i)
		{
			for (int j = 0; j < this.getColumn(); ++j)
			{
				if (this.getElement(i, j) != a.getElement(i, j)) 
				{
					return false;
				}
			}
		}
		return true;
    }
    public String toString()
	{
		StringBuilder res = new StringBuilder(this.getRow() * this.getColumn());
		for (int i = 0; i < this.getRow(); ++i)
		{
			for (int j = 0; j < this.getColumn(); ++j)
			{
				res.append(this.getElement(i, j)).append(" ");
			}
			res.append("\n"); 
		}
		return res.toString();
	}
}