
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HighScore {
	
	private String s;
	private static List<Float> hScores;
	
	public HighScore(String filename) throws IOException {
		

		if (filename == null) {
			throw new IllegalArgumentException();
		}
		
		FileReader fr = new FileReader(filename);
		
		BufferedReader r = new BufferedReader(fr);
		
		s = r.readLine();
		
		hScores = new ArrayList<Float>();
		
		while (s != null) {
			if (s.isEmpty()) {
				s = r.readLine();
			}
			
		
			float score = Float.parseFloat(s);
			
			hScores.add(score);
			
			s = r.readLine();
		}
		
		r.close();
		
	}
	
	public float getHighScore() {
		Collections.sort(hScores);
		
		float high = hScores.get(0);
		
		return high;
	}
	
	public void setHighScore(Float score) {		
		try {
			FileWriter fw = new FileWriter("scores.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
		    out.println(Float.toString(score));
		    out.close();
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}
	}
	
	
}
