package lock;
import java.util.*;
import java.io.*;

public class CalculateWords
{
	HashMap<String, Integer> words;
	String[] files;
	MyThread[] threads;
	int numThread = 0;
	CalculateWords()
	{}
	CalculateWords(String[] files, int thr)
	{
		this.files = files;
		this.threads = new MyThread[thr];
		this.words = new HashMap<String, Integer>();
	}
	public void findWords()
	{
		for (int i = 0; i < threads.length; i++)
		{
			threads[i] = new MyThread(files[i]);
			System.out.println(files[i]);
			threads[i].start();
		}
	}
	public class MyThread extends Thread
	{
		String file;
		ArrayList<String> wordsFromFile;

		MyThread(String file) // считываение файла
		{
			this.file = file;
			this.wordsFromFile = new ArrayList<String>();
			File myFile = new File(file);
			try
			{
				FileReader reader = new FileReader(file);
				int c = 0;
				String word = "";
				while ((c = reader.read()) != -1)
			    {
			       	if ((char)c != ' ' && (char)c != '\n')
			       	{
			       		word = word + (char)c;
			       	}
			       	else 
			       	{
			       		this.wordsFromFile.add(word);
			       		word = "";
			       	}
		    	}
			}
			catch (Exception e)
			{

			}

		}

		@Override
		public void run()
		{
			synchronized(words)
			{
				System.out.println(Thread.currentThread().getName());
				for (int i = 0; i < wordsFromFile.size(); i++)
		        {
		        	int isnt = 0;
		        	for (Map.Entry it1 : words.entrySet())
		        	{
		        		if (it1.getKey().equals(wordsFromFile.get(i)))
		        		{
		        			Integer v = (Integer)it1.getValue();
		        			v++;
		        			words.put(wordsFromFile.get(i), v);

		        		}
		        		else
		        			isnt++;
		        	}
		        	if (isnt == words.size() || words.size() == 0)
		        	{
		        		words.put(wordsFromFile.get(i), 1);
		        	}
		        }
		        if (numThread == files.length - 1)
		        {
		        	StringBuilder res = new StringBuilder();
		        	System.out.println("\nWords: " + words.size());
					for (Map.Entry it1 : words.entrySet())
			    	{
			    		System.out.println(it1.getKey() + " " + it1.getValue());
			    		res.append(it1.getKey()).append(" ").append(it1.getValue()).append("\n");
			    	}
			    	try
			    	{
			    		this.saveFile(res.toString());
			    	}
			    	catch(Exception exp)
			    	{

			    	}
			    }
			    numThread++;
		    }
		}
		private void saveFile(String res) throws Exception
		{
			String fileres = "C:\\javalab\\lab9\\files\\out.txt";
	    	File myFileres = new File(fileres);
			FileWriter writer = new FileWriter(fileres, false);
			if (myFileres.isFile() == false) throw new Exception("file txt not found");
	        writer.write(res.toString());
	        writer.close();
		}
	}
	
}