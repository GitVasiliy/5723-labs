package stream;
import java.lang.*;
import java.util.*;

public class MergeSort extends Thread
{
	int numThreads;
	Comparable[] array;
	Comparable[] res;
	int avaibleThr;

	@Override
	public void run() //запуск из мейна
	{
		System.out.println(Thread.currentThread().getName() + " " + array.length);
		sort();
	}
	MergeSort()
	{}
	MergeSort(Comparable[] array, int thr)
	{
		this.array = array;
		this.numThreads = thr;
		this.avaibleThr = thr;
		this.res = new Comparable[array.length];
	}
	public void sort() //запуск из мейна
	{
		if (this.array.length <= 1) 
		{
			this.res = this.array;
			return;
		}
		int mid = this.array.length/2;
		Comparable[] leftArray = new Comparable[mid];
		Comparable[] rightArray = new Comparable[this.array.length - mid];
		System.arraycopy(this.array, 0, leftArray, 0, mid);
    	System.arraycopy(this.array, mid, rightArray, 0, this.array.length - mid);
		if (this.avaibleThr <= 1)
		{
        	this.avaibleThr--;
        	MergeSort leftThr = new MergeSort(leftArray, 1);
        	MergeSort rightThr = new MergeSort(rightArray, 1);
        	leftThr.sort();
        	rightThr.sort();
        	this.res = merge(leftThr.getResult(), rightThr.getResult());
		}
		if (this.avaibleThr > 1) //создаем потоки с массивами и запускаем
		{
        	this.avaibleThr -= 2;
			MergeSort leftThr = new MergeSort(leftArray, this.avaibleThr);
			this.avaibleThr -= 2;
			MergeSort rightThr = new MergeSort(rightArray, this.avaibleThr);
			leftThr.start();
			rightThr.start();
			try
			{
				leftThr.join();
				rightThr.join();

				res = merge(leftThr.getResult(), rightThr.getResult());
			}
			catch(Exception exp)
			{
				System.out.println(exp.getMessage());
			}
		}
	}
	//////////////////////////////////
	private Comparable[] merge(Comparable[] leftAr, Comparable[] rightAr) //здесь сливать, готово
	{
		int leftCur = 0;
		int rightCur = 0;
		int c = 0;
		Comparable[] result = new Comparable[leftAr.length + rightAr.length];
		while (leftCur < leftAr.length && rightCur < rightAr.length)
		{
			if (leftAr[leftCur].compareTo(rightAr[rightCur]) <= 0)
			{
				result[c] = leftAr[leftCur];
				leftCur++;
			}
			else
			{	
				result[c] = rightAr[rightCur];
				rightCur++;
			}
			c++;
		}
		if (leftCur < leftAr.length)
		{
			System.arraycopy(leftAr, leftCur, result, c, result.length - c);
		}
		if (rightCur < rightAr.length)
		{
			System.arraycopy(rightAr, rightCur, result, c, result.length - c);
		}
		return result;
	}
	private Comparable[] getResult()
	{
		return res;
	}
	public String toString()
	{
		StringBuilder res = new StringBuilder(array.length);
		for (int i = 0; i < array.length; ++i)
			res.append(this.res[i]).append(" ");

		return res.toString();
	}
	
}

