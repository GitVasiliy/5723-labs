package matrix;

import java.lang.Thread;

public class MultiplicationCells extends Thread {

	UsualMatrix firstMatrix;
	UsualMatrix secondMatrix;
	UsualMatrix result;
	int beginIndex;
	int endIndex;
	
	public MultiplicationCells(UsualMatrix firstMatrix, UsualMatrix secondMatrix, UsualMatrix result, int beginIndex, int endIndex) 
	{
		this.firstMatrix = firstMatrix;
		this.secondMatrix = secondMatrix;
		this.result = result;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}
	
	@Override
	public void run() {
		if(firstMatrix.getColumns() != secondMatrix.getRows()) {
			throw new RuntimeException("In thread incorrect matrix!");
		}
		
		if(result.getRows() != firstMatrix.getRows() || result.getColumns() != secondMatrix.getColumns()) {
			throw new RuntimeException("In thread incorrect matrix");
		}
		
		int lastIndex = result.getRows() * result.getColumns();
		for(int index = beginIndex; (index < endIndex) && (index < lastIndex); index++) {
			int i = index / result.getColumns();
			int j = index % result.getColumns();
			result.setElement(i, j, 0);
			for(int k = 0; k < firstMatrix.getColumns(); k++) {
				result.setElement(i, j, result.getElement(i, j) + (firstMatrix.getElement(i, k) * secondMatrix.getElement(k, j)));
			}
		}
	}
	
}