package ca0;

import utilities.FileLoader;

import java.io.IOException;
import java.util.List;

public class CA0 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<String> list = FileLoader.readListFromFile("ca0.txt");
			for(String item : list) {
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
