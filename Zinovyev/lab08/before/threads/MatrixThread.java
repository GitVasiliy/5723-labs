package threads;

import java.lang.Thread;

public class MatrixThread extends Thread {
    
	private UsualMatrix m1;
    private UsualMatrix m2;
    private UsualMatrix result;
    private int firstIndex;
    private int secondIndex;
    
    public MatrixThread(UsualMatrix mx1, UsualMatrix mx2, UsualMatrix res, int first, int second){
        firstIndex = first;
        secondIndex = second;
        m1 = mx1;
        m2  = mx2;
        result = res;
    }
    
   @Override
   public void run(){
       int tmp = 0;
       for(int i = firstIndex; i < secondIndex; i++){
           for(int j = 0; j < m2.getColumns(); j++){
                    for(int z = 0; z < m1.getColumns(); z++){
                        tmp = m1.getElement(i, z) * m2.getElement(z, j);
                        tmp += result.getElement(i, j);
                        result.setElement(i, j, tmp);
                    }
           }
       }
       
   }
    
   public UsualMatrix getResult(){
       return result;
   }
}
