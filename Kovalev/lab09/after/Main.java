import java.util.Scanner;

public class Main{
	public static void main(String[] args) throws InterruptedException{
		boolean b = true;
		Scanner scanner = new Scanner(System.in);
		int number = 0;
		System.out.print("Input the number of PC: ");
		int n = scanner.nextInt();
		Do l = new Do(n);
		System.out.print("Input the number of people: ");
		int number_of_people = scanner.nextInt();
		ResourcePool []s = new ResourcePool[number_of_people];
		Calendar1 []c = new Calendar1[n];
		for (int i = 0; i < n; i++){
			c[i] = new Calendar1();
		}
		while (b == true){
			for (int i = 0; i < n; i++){
				if (l.get(i) == false){
					l.set(true, i);
					s[number] = new ResourcePool(number, i, l, c[i]);
					s[number].start();
					number++;
				}
			}
			if (number == number_of_people) b = false;
		} 
	}
}