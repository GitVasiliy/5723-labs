public class ParallelMatrixProduct extends Thread {
	private int countFlow;
	private UsualMatrix matrixOne;
	private UsualMatrix matrixTwo;
	private UsualMatrix matrixResult;
	private int begin;
	private int end;

	public ParallelMatrixProduct (int countFlow, UsualMatrix matrixOne, UsualMatrix matrixTwo, UsualMatrix matrixResult, int begin, int end) {
		this.countFlow = countFlow;
		this.matrixOne = matrixOne;
		this.matrixTwo = matrixTwo;
		this.matrixResult = matrixResult;
		this.begin = begin;
		this.end = end;
	}

	public void run() {
		for(int i = 0; i < matrixOne.getR(); i++) {
			for(int j = begin; j < end; j++) 
			{
				int sum = 0;
				for (int k = 0; k < matrixTwo.getR(); k++) {
					sum += matrixOne.getElement(i, k) * matrixTwo.getElement(k, j);
				}
				matrixResult.setElement(i, j, sum);
			}
		}
	}
}