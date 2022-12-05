package ca3;

import java.io.IOException;
import java.util.List;

import utilities.FileLoader;

public class CA3 {
	private static String alphabetOrder = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<String> list = FileLoader.readListFromFile("ca3.txt");
			int priority = 0;
			int incr = 0;
			String s1 = "", s2 = "", s3 = "";
			for(String string : list) {
				incr++;
				// char item = getCommonItemSameLine(string);
				char item = 0;
				switch(incr % 3) {
				case 0:
					s3 = string;
					item = getItem(s1, s2, s3);
					break;
				case 1:
					s1 = string;
					break;
				case 2:
					s2 = string;
					break;
				}

				if(item > 0) {					
					priority += alphabetOrder.indexOf(item) + 1;
				}
			}
			System.out.println(priority);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static char getCommonItemSameLine(String string) {
		String a = string.substring(0, string.length()/2);
		String b = string.substring(string.length()/2, string.length());
		char item = getCommonItem(a, b);
		return item;
	}

	private static char getCommonItem(String a, String b) {
		for(char c : a.toCharArray()) {
			if(b.indexOf(c) >= 0) {
				return c;
			}
		}
		return 0;
	}

	private static char getItem(String s1, String s2, String s3) {
		for(char c : s1.toCharArray()) {
			if(s2.indexOf(c) >= 0 && s3.indexOf(c) >= 0) {
				return c;
			}
		}
		return 0;
	}

}
