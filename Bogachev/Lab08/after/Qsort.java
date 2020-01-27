import java.util.Random;
import java.util.Arrays;


public class Qsort extends Thread{
	private Object[] arr;
	private int low;
	private int high;
	private static int threadsCount = 0;

	public Qsort(Object[] array, int lower, int higher, int threads) {
		arr = array;
		low = lower;
		high = higher;
		threadsCount = threads;
	}

	public Qsort(Object[] array, int lower, int higher) {
		arr = array;
		low = lower;
		high = higher;
	}

	public static void main (String[] agrs) {
		Random generator = new Random();
		int s = 1000000;
		Integer[] array = new Integer[s];
		Integer[] arr = new Integer[s];
		for (int i = 0; i < array.length; i++) {
			array[i] = generator.nextInt(100000);
			arr[i] = array[i];	
		}
		//System.out.println(Arrays.toString(arr)); 
		System.out.println("Without parallel: ");
		long start = System.nanoTime();
		quicksort(array, 0, array.length - 1);
		long finish = System.nanoTime();
		//System.out.println(Arrays.toString(array));
		System.out.println("Time: " + (finish - start));
		System.out.println();	
		System.out.println("With parallel: ");
		try { 
			Qsort qSort = new Qsort(arr, 0, arr.length - 1, 12);
			start = System.nanoTime();
			qSort.run();
			finish = System.nanoTime();
			//System.out.println(Arrays.toString(arr));
			System.out.println("Time: " + (finish - start));
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			parralelQuickSort(arr, low, high);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parralelQuickSort(Object[] array, int lower, int higher) throws Exception {
		if (lower >= higher) {
			return;
		}

		int p = partition(array, lower, higher);
		
		if (threadsCount > 0) {
			Qsort quicksortObject1 = new Qsort(array, lower, p);
		
			quicksortObject1.start();
			threadsCount--;

			if (threadsCount > 0) {
				Qsort quicksortObject2 = new Qsort(array, p + 1, higher);
		
				quicksortObject2.start();
				threadsCount--;
				quicksortObject2.join();
			} else {
			 	quicksort(array, p + 1, higher);
			}

			quicksortObject1.join();
		} else {
			quicksort(array, lower, p);
		}

	}

	public static void quicksort(Object[] array, int lower, int higher) {
		if (lower < higher) {
			int p = partition(array, lower, higher);
			quicksort(array, lower, p);
			quicksort(array, p + 1, higher);
		}
	}

	private  static int partition(Object[] array, int lower, int higher) {
		int middle = lower + (higher - lower) / 2;
        Object middleElem = array[middle];
		int i = lower;
		int j = higher;

		while (true) {
			while (( (Comparable) array[j]).compareTo(middleElem) > 0)
				j--;
			
			while (( (Comparable) array[i]).compareTo(middleElem) < 0 && i <= j)
				i++;

			if (i >= j) {

				return j;
			}

			Object tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;


			i++;
			j--;
		}
	}
}