import java.util.Random;
import matrix.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		ParallelMatrixProduct firstMatrix = new ParallelMatrixProduct(800, 900, 1);
		ParallelMatrixProduct secondMatrix = new ParallelMatrixProduct(900, 800, 1);			
		getRandMatrix(firstMatrix, 10);
		getRandMatrix(secondMatrix, 10);
		System.out.println("Without threads: ");
		System.out.println();
		long start = System.nanoTime();
		firstMatrix.product(secondMatrix);
		long finish = System.nanoTime();
		System.out.println("Time: " + (finish - start));
		System.out.println();
		System.out.println("With threads: ");
		System.out.println();
		//for(int i = 2; i <= 50; i++) {
			firstMatrix.setCountThreads(7);
			start = System.nanoTime();
			firstMatrix.productParallel(secondMatrix);
			finish = System.nanoTime();
			//System.out.println("Threads: " + i);
			System.out.println("Time: " + (finish - start));
			System.out.println();
		//}
	}

	public static void getRandMatrix(UsualMatrix matrix, int module) {
		Random rand = new Random();
		for(int i = 0; i < matrix.getRows(); i++) {
			for(int j = 0; j < matrix.getColumns(); j++) {
				matrix.setElement(i, j, rand.nextInt() % module);
			}
		}
	}
	
}