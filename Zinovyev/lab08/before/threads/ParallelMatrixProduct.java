package threads;

public class ParallelMatrixProduct {
  
   private UsualMatrix m1;
   private UsualMatrix m2;
   private UsualMatrix res;
   private MatrixThread [] thr;
   private int threadNumber;
   
   public ParallelMatrixProduct(UsualMatrix first, UsualMatrix second, int n){
       m1 = first;
       m2 = second;
       res = new UsualMatrix(m1.getRows(), m2.getColumns(), false);
       threadNumber = n;
       thr = new MatrixThread[threadNumber];
   }
   
   public UsualMatrix startProduct() throws InterruptedException{
       if(m1.getColumns() != m2.getRows()){
            throw new RuntimeException("product is inpossible");
        }
       //last - first = ����� ���-�� ������� �������� ������� ������ �� ������
       int first = 0;
       int last = 0;
       int row = m1.getRows();
       for(int i = 0; i < threadNumber ; i++){
    	   //��������� ��������� �� �����
           if(threadNumber - 1 == i ){
        	   //��� ���������� -- ������ ��� ���, ��� ��������
               last = first + ((row/threadNumber) + (row % threadNumber));
           }
           //���� ��� -- �� ������������ �����
           else{
                last = first + row/threadNumber;
           }
           //�������� ������ �� ����� �����(����� new)
           thr[i] = new MatrixThread(m1, m2, res, first, last);
           //��������� ���
           thr[i].start();
           //������ ������� �������
           first = last;
       }
       //��� ������� ������
       for(int i = 0; i < threadNumber; i++){
    	   //���������
    	   thr[i].join();
    	   //�������� ��������������
           res = thr[i].getResult();
       }        
       //���� � main
       return res;
   } 
   
   public UsualMatrix productOneThread(){
	    if(m1.getColumns() != m2.getRows()){
	        throw new RuntimeException("product is inpossible");
	    }
		int tmp;
		for(int i = 0; i < m1.getRows(); i++){
            for(int j = 0; j < m2.getColumns(); j++){
                for(int k = 0; k < m1.getColumns(); k++){
                    tmp = m1.getElement(i,k) * m2.getElement(k,j);
                    tmp += res.getElement(i,j);
                    res.setElement(i,j,tmp);
                }
            }
		}
	    return res;
	}
   
}
