package _2022.ca4;

import java.io.IOException;
import java.util.List;

import utilities.FileLoader;

public class CA4 {

	public static void main(String[] args) {
		try {
			List<String> list = FileLoader.readListFromFile("ca4.txt");
			int incr=0;
			for(String string : list) {
				String s1 = string.split(",")[0];
				String s2 = string.split(",")[1];
				int i11 = Integer.parseInt(s1.split("-")[0]);
				int i12 = Integer.parseInt(s1.split("-")[1]);
				int i21 = Integer.parseInt(s2.split("-")[0]);
				int i22 = Integer.parseInt(s2.split("-")[1]);
				Range range1 = new Range(i11, i12);
				Range range2 = new Range(i21, i22);
				System.out.print(range1 + " " + range2);
				if(range1.contains(range2) || range2.contains(range1)) {
					incr++;
				}
			}
			System.out.println(incr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
