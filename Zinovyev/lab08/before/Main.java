import threads.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
    	int size = 1000;
        UsualMatrix matrix1 = new UsualMatrix(size, size, true);
        UsualMatrix matrix2 = new UsualMatrix(size, size, true);
        UsualMatrix matrix3 = new UsualMatrix(size, size, true);
        UsualMatrix matrix4 = new UsualMatrix(size, size, true);
        
        int countNumber = 5;
        
        ParallelMatrixProduct coordinator = new ParallelMatrixProduct(matrix1, matrix2, countNumber);
        ParallelMatrixProduct coordinator2 = new ParallelMatrixProduct(matrix3, matrix4, countNumber);
        
        try{
            UsualMatrix result2 = coordinator.startProduct();
            
            long l = System.nanoTime();
            UsualMatrix result1 = coordinator.productOneThread();
            long l2 = System.nanoTime();
            System.out.println("time matrix product (one thread) = " + (l2 - l));   
            
            l = System.nanoTime();
            UsualMatrix result3 = coordinator2.startProduct();
            l2 = System.nanoTime();
            System.out.println("time matrix product (" + countNumber + " thread) = " + (l2 - l));   

        }
        catch(RuntimeException ex){
            ex.getMessage();
        }
    }
    
}
