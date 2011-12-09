package helper;

import java.util.ArrayList;

public class Messwerte {
	
	ArrayList<Integer> messwerte = new ArrayList<Integer>();
	private final int SIZE;
	
	public Messwerte(int size) {
		SIZE = size;
	}
	
	public void add(int wert) {
		if (messwerte.size() > SIZE) messwerte.remove(0);
		messwerte.add(wert);
	}
	
	public void clear() {
		messwerte.clear();
	}
	
	public int getAverage() {
		int sum = 0;
		for (int i: messwerte) sum += i;
		int average = sum / messwerte.size();
		return average;
	}
}
