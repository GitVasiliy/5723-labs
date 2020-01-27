public class DeadlockThread extends Thread {
	Integer firstNumber;
	Integer secondNumber;

	public DeadlockThread (Integer firstNumber, Integer secondNumber) {
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
	}

	@Override
	public void run () {
		synchronized (firstNumber) {
			System.out.println("Working...");
			synchronized (secondNumber) {
				System.out.println(firstNumber + secondNumber);
			}
		}
	}
}