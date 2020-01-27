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
       //last - first = какое кол-во строчек отдается каждому потоку на работу
       int first = 0;
       int last = 0;
       int row = m1.getRows();
       for(int i = 0; i < threadNumber ; i++){
    	   //проверяем последний ли поток
           if(threadNumber - 1 == i ){
        	   //есл посследний -- отдаем ему все, что осталось
               last = first + ((row/threadNumber) + (row % threadNumber));
           }
           //если нет -- то определенную часть
           else{
                last = first + row/threadNumber;
           }
           //выделяем память на новый поток(слово new)
           thr[i] = new MatrixThread(m1, m2, res, first, last);
           //запускаем его
           thr[i].start();
           //меняем местами индексы
           first = last;
       }
       //для каждого потока
       for(int i = 0; i < threadNumber; i++){
    	   //запускаем
    	   thr[i].join();
    	   //забираем результирующую
           res = thr[i].getResult();
       }        
       //идем в main
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
