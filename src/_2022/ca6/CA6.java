package _2022.ca6;

import utilities.FileLoader;

import java.io.IOException;
import java.util.List;

public class CA6 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<String> list = FileLoader.readListFromFile("ca6.txt");
			for(String item : list) {
				// findFirstPacket(item, 1);
				findFirstPacket(item, 14);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void findFirstPacket(String item, int size) {
		int incr = 0;
		boolean found = false;
		while(!found && incr < item.length() - size) {
			String subStr = item.substring(incr, incr + size);
			long count = subStr.chars().distinct().count();
			if(count == size) {
				System.out.println(incr + size);
				found = true;
			}
			incr++;
		}
	}
}
