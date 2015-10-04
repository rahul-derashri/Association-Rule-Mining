import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * This class reads the data from the file and load into ArrayList of ArrayList
 * 
 * @author Hitpal Kaur
 *
 */
public class DataReader {
	public ArrayList<ArrayList<String>> generateTableFromFile(){
		String[][] genedata = new String[100][101];
		ArrayList<ArrayList<String>> all=new ArrayList<ArrayList<String>>();
		try{
			Path path = Paths.get("association.dat");
			BufferedReader br = new BufferedReader(new FileReader(path.toAbsolutePath().toString()));
			// above statement is used  to take data from data-set file
			//Path path = Paths.get("."+File.separator+"data"+File.separator+"association.dat");
			//System.out.println("Path: "+path.toAbsolutePath());
			String line = "";
					for (int i = 0 ; i <= 99 ; i++){
					line = br.readLine();
					String tempstring[] = line.split("\\t");
					ArrayList<String> al=new ArrayList<String>();
					for (int j=1; j<=101 ;j++){
						String s=tempstring[j];
						al.add(s);
					}
					all.add(al);
				}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		ArrayList<ArrayList<String>> newMatrix = new ArrayList<ArrayList<String>>();
		
		for(int i=0;i<all.size();i++){
			ArrayList<String> k=all.get(i);
			ArrayList<String> a=new ArrayList<String>();
			int count=0;
			for(String s:k){
				if(s.startsWith("U")) a.add("gene_"+(int)(count+1)+"_up");
				else if(s.startsWith("D")) a.add("gene_"+(int)(count+1)+"_down");
				else if(s.startsWith("AL")) a.add("A");
				else if(s.startsWith("AM")) a.add("M");
				else if(s.startsWith("B")) a.add("B");
				else if(s.startsWith("C")) a.add("C");
				else a.add(s);
				count++;
			}
			newMatrix.add(a);
		}
		return newMatrix;
	}
}
