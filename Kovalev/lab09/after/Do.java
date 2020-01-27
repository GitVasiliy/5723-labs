public class Do{
	private boolean []p;

	public Do(){}
	public Do(int numberPC){
		p = new boolean[numberPC];
		for (int i = 0; i < numberPC; i++){
			p[i] = false;
		}
	}

	public void set(boolean l, int i){
		p[i] = l;
	}

	public boolean get(int i){
		return p[i];
	}

	public void print(){
		for (int i = 0; i < p.length; i++){
			System.out.println (p[i]);
		}
	}
}