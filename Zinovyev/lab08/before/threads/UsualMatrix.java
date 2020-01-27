
package threads;

import java.util.Random;

public class UsualMatrix {
    private int[][] matrix;
    private final int row;
    private final int col;
	
    //Конструктор заполняющий рандомными значениями - true
    //нулевая матрица с false
    public UsualMatrix(int r,int c, boolean key){
    	//проверка на параметры, вдруг значение колонок или столбцов меньше 0
    	if(r <= 0 || c <= 0) {
    		throw new RuntimeException("impossible create matrix.");
    	}
    	//выделяем память на матрицу
		matrix = new int[r][c];
		//сохраняем количество строк
		row = r;
		//сохраняем кол-во столбцо
		col = c;
		
		//тут проверяем, если true -- заполняем рандомными числами
		//если false -- выходим отсюда науй
		if(key) {
			final Random random = new Random();
			for(int i = 0; i < row; i++) {
	            for(int j = 0; j < col; j++) {
	            	//заполняем рандомными значениями
	                matrix[i][j] = Math.abs(random.nextInt()) % 5;
	            }
			}
		}
    }

    //устанавливает значение value на позицию с координатами [r][c]
   public void setElement(int r, int c, int value) {
	   if((r >= row) || (c >= col) ){
			throw new RuntimeException("out of range matrix (set element)");
		}
		matrix[r][c] = value;
   }

    public int getElement(int r, int c){
		if((r >= row) || (c >= col) ){
			throw new RuntimeException("out of range matrix (get element)");
		}
		return this.matrix[r][c];
    }
    
    @Override
    public final String toString(){
       StringBuilder str = new StringBuilder();
		for(int i = 0; i < row; i++) { 
			for(int j = 0; j < col; j++) { 
				str.append(matrix[i][j] + " "); 
			} 
			str.append("\r\n"); 
		}
		return str.toString(); 
	}
    
    
    public int getRows(){
        return row;
    }
    
    public int getColumns(){
        return col;
    }
}