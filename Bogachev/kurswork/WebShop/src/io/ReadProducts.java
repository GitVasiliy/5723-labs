package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import exception.WebShopException;
import model.Product;

public class ReadProducts {

	private BufferedReader br;
	private String file;
	
	public ReadProducts(String file){
		this.file = file;
	}
	
	public ArrayList<Product> readFile() throws IOException {
		open();
		ArrayList<Product> res = new ArrayList<Product>();
		String line;
		while((line = br.readLine()) != null) {
			String [] data = line.split("; ");
			if(data.length != 6) {
				System.err.println("incorrect data base format| not all arguments of product");
				throw new WebShopException("incorrect data base format| not all arguments of product");
			}
			String name = data[0];
			Integer cost = 0;
			try {
				cost = Integer.parseInt(data[1]);
			}catch(NumberFormatException ex) {
				System.err.println("incorrect data base format| second argument has to be a cost");
				throw new WebShopException("incorrect data base format| second argument has to be a cost");
			}
			String dateAdded = data[2];
			Integer numberOfProduct = 0;
			try {
				numberOfProduct = Integer.parseInt(data[3]);
			}catch(NumberFormatException ex) {
				System.err.println("incorrect data base format| third argument has to be a number of products");
				throw new WebShopException("incorrect data base format| third argument has to be a number of products");
			}
			String description = data[4];
			String category = data[5];
			res.add(new Product(name, cost, dateAdded, numberOfProduct, description, category));
		}
		
		close();
		return res;
	}
	
	public void close() throws IOException {
		br.close();
	}
	
	public void open() throws FileNotFoundException {
		br = new BufferedReader(
	            new InputStreamReader(
	                    new FileInputStream(file)));
	}
	
}
