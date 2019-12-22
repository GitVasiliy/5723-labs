public class UsualMatrix {
    protected int[][] m;
    protected int rows = 0;
    protected int cols = 0;

    public UsualMatrix (int r, int c) {
        rows = r;
        cols = c;
        m = new int[r][c];
    }

    public void setVal (int r, int c, int v) throws MyException
    {
        if (r >= rows || r < 0 || c >= cols || c < 0) {
            throw new MyException (new String("Bad row or column while setting"));
        }
        m[r][c] = v;
    }

    public int getVal (int r, int c) throws MyException
    {
        if (r >= rows || r < 0 || c >= cols || c < 0) {
            throw new MyException (new String("Bad row or column while getting"));
        }
        return m[r][c];
    }

    public int getR() { return rows; }
    public int getC() { return cols; }

    public boolean equals (Object v)
    {
        if (this == v)
            return true;
        if (v == null || v.getClass() != this.getClass())
            return false;

        UsualMatrix t = (UsualMatrix) v;

        if (rows != t.getR() || cols != t.getC()) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (getVal(i, j) != t.getVal(i, j)) return false;
            }
        }
        return true;
    }

    //================================================================

    public UsualMatrix product(UsualMatrix v) throws MyException {
        if (this.getC() != v.getR()) {
            throw new MyException(new String("Prod ex"));
        }

        UsualMatrix t = new UsualMatrix(this.getR(), v.getC());

        for (int i = 0; i < getR(); i++){
            for (int j = 0; j < v.getC(); j++) {
                int temp = 0;
                for (int k = 0; k < this.getC(); k++) {
                    temp += (this.getVal(i, k) * v.getVal(k, j));
                }
                t.setVal(i, j, temp);
            }
        }
        return t;
    }

    //=================================================================

    public String toString ()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getR(); i++) {
            sb.append("[ ");
            for (int j = 0; j < getC(); j++) {
                int t = getVal(i, j);
                if (t < 100) sb.append(' ');
                if (t < 10) sb.append(' ');
                sb.append(t);
                sb.append(' ');
            }
            sb.append("]\n");
        }
        sb.append('\n');
        String s = sb.toString();
        return s;
    }
}