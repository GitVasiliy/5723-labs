public class Main {
	public static void main(String[] args) {
		Integer firstNumber = 10;
		Integer secondNumber = 20;
		DeadlockThread thread1 = new DeadlockThread(firstNumber, secondNumber);
		DeadlockThread thread2 = new DeadlockThread(secondNumber, firstNumber);
		thread1.start();
		thread2.start();
	}
}