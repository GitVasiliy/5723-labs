public class UsualMatrix {
	
    protected int[][] m;

    public UsualMatrix (int r, int c) {
		m = new int[r][c];
	}
	
	public UsualMatrix product (UsualMatrix v) 
	{

		UsualMatrix temp = new UsualMatrix(getR(), v.getC());
		
		for(int i = 0; i < getR(); i++) {
			for(int j = 0; j < v.getC(); j++) 
			{
				for (int k = 0; k < v.getR(); k++)
					temp.setElement (i, j, temp.getElement(i, j) + getElement(i, k) * v.getElement(k, j));
			}
		}
		return temp;
	}
	
	public void setElement (int row, int column, int value)
	{
		m[row][column] = value;
	}
	
	public int getElement (int row, int column)
	{
		return m[row][column];
	}
	
	public String toString () 
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getR(); i++) {
			sb.append("[ ");
			for (int j = 0; j < getC(); j++) {
				sb.append(getElement(i, j));
				sb.append(' ');
			}
			sb.append("]\n");
		}
		sb.append('\n');
		String s = sb.toString();
		return s;
	}
	
	public int getR(){
		
		return m.length;
	}
	
	public int getC() {
		return m[0].length;
	}

	public int[] countProduct(int countFlow) {
		int[] m = new int [countFlow];
		int j = 0;
		for (int i = 0; i < getC(); i++) {
			m[j]++;
			j++;
			if (j == countFlow) j = 0;
		}
		return m;
	}
}