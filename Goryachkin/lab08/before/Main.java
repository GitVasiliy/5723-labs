import java.io.IOException;
import java.util.Random;

public class Main {
	public static void main(String [] args) throws InterruptedException {
		Random r = new Random();
		UsualMatrix one = new UsualMatrix(10, 10);
		UsualMatrix two = new UsualMatrix(10, 10);
		for(int i = 0; i < one.getR(); i++) {
			for(int j = 0; j < one.getC(); j++) {
				one.setElement(i, j, r.nextInt(10));
				two.setElement(i, j, r.nextInt(10));
			}
		}
		//System.out.println(one);
		//System.out.println(two);
		UsualMatrix result = new UsualMatrix(one.getR(), two.getC());
		long time1 = System.currentTimeMillis();
		System.out.println(one.product(two));
		long time2 = System.currentTimeMillis();
		int countFlow = 3;
		ParallelMatrixProduct [] flows = new ParallelMatrixProduct [countFlow];
		int [] m = new int [countFlow];
		m = two.countProduct(countFlow);
		int begin = 0;
		int end = m[0];
		long time3 = System.currentTimeMillis();
		for (int i = 0; i < countFlow; i++) {
			flows[i] = new ParallelMatrixProduct(countFlow, one, two, result, begin, end);
			flows[i].start();
			begin += m[i];
			if (i < countFlow - 1) end += m[i + 1];
		}
		for (ParallelMatrixProduct pmp : flows) {
			pmp.join();
		}
		long time4 = System.currentTimeMillis();
		System.out.println(result);
		System.out.println("single-threaded multiplication: " + (time2 - time1) + " milliseconds");
		System.out.println(countFlow + " flows: " + (time4 - time3)  + " milliseconds");
	}
}