package mazeworld;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Maze{
	String filename;
	public boolean[][] cells;
	List<String> mazeStrings;
	public int rows;
	public int cols;
	
	public Maze(){
		rows = 0;
		cols = 0;
		mazeStrings = new ArrayList<String>();
	}
	
	public void readFromFile(String inputFileName){
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFileName));
			String line = "";
			line = in.readLine();
			
			while (line != null){
				rows++;
				System.out.println(line);
				cols = Math.max(cols, line.length());
				mazeStrings.add(new String(line));
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.reverse(mazeStrings);
		cells = new boolean[cols][rows];
		
		for (int i = 0; i < rows; i++){
			int j = 0;
			for (; j < mazeStrings.get(i).length(); j++){
				if (mazeStrings.get(i).charAt(j) == '.'){
					cells[j][i] = true;
					//System.out.print("(" + j + ", " + i + ")" + ".");
				}
				else if (mazeStrings.get(i).charAt(j) == '#'){
					cells[j][i] = false;
					//System.out.print("(" + j + ", " + i + ")" + "#");
				}
				else{
					System.out.println("error occurs when loading maze at (" + j + ", " + i +")");
				}
			}
			for (; j < cols; j++){
				cells[j][i] = false;
				//System.out.print("(" + j + ", " + i + ")" + "#");
			}
			//System.out.println("");
		}
	}
	
	public void generateRandomMaze(String outputFileName, int rows, int cols){
		try {
			BufferedWriter out= new BufferedWriter(new FileWriter(outputFileName));
			for (int i = 0; i < rows; i++){
				for (int j = 0; j < cols; j++){
					if (Math.random() < 0.2){
						out.write("#");
					}
					else{
						out.write(".");
					}
				}
				out.newLine();
				out.flush();
			}
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
