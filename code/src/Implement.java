import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;



/**
 * This class generates Frequent Item-sets
 * 
 * @author Sagar Shinde
 *
 */
public class Implement {
	static int MIN_SUPPORT=0, MIN_CONFIDENCE=0, lCount=0, cCount=1, total=0;
	
	public static void main(String[] args) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Please enter the support percentage and press enter key");
			String s = br.readLine();
			if(s.endsWith("%")) s=s.substring(0,s.length()-1);
			MIN_SUPPORT = Integer.parseInt(s);
			
			System.out.println("Please enter the confidence percentage and press enter key");
			s = br.readLine();
			if(s.endsWith("%")) s=s.substring(0,s.length()-1);
			MIN_CONFIDENCE = Integer.parseInt(s);
			
			DataReader dataReader = new DataReader();
			// This function gets the data from data-set file and stores in ArrayList of ArrayList
			ArrayList<ArrayList<String>> matrix = dataReader.generateTableFromFile();
			
			// This function generates candidates from input data-set
			ArrayList<ArrayList<String>> candidates = generateCandidates(matrix);
			RuleGeneration generation = new RuleGeneration(matrix);
			generation.processSets(candidates);
			
			System.out.println("\nTotal number of rules generated: "+generation.getRules().size());
			String input = "";
			Query query = new Query(generation.getRules());
			
			Set<Rule> resultSet = null;
			boolean isManualQuery = true;
			while(!input.equalsIgnoreCase("4")){
				isManualQuery = true;
				System.out.println("\n************************************************");
				System.out.println("TEMPLATE 1: {RULE|BODY|HEAD} HAS {ANY|NUMBER|NONE} OF (ITEM1, ITEM2, ..., ITEMn)");
				System.out.println("TEMPLATE 2: SizeOf({BODY|HEAD|RULE}) >= NUMBER");
				System.out.println("TEMPLATE 3: TEMPLATE 1/2 AND/OR TEMPLATE 1/2");
				System.out.println("************************************************");
				
				System.out.println("Please select from options below:");
				System.out.println("1) Enter 1 to enter query");
				System.out.println("2) Enter 2 to select from sample queries");
				System.out.println("3) Enter 3 to display all rules");
				System.out.println("4) Enter 4 to Exit");
				input = br.readLine();
				String tempInput = "";
				String originalQuery = "";
				if( input.equalsIgnoreCase("2") ){
					isManualQuery = false;
					System.out.println("Enter 1 to RUN TEMPLATE 1 QUERY -> [RULE HAS ANY OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 2 to RUN TEMPLATE 1 QUERY -> [RULE HAS NONE OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 3 to RUN TEMPLATE 1 QUERY -> [RULE HAS 1 OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 4 to RUN TEMPLATE 1 QUERY -> [BODY HAS ANY OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 5 to RUN TEMPLATE 1 QUERY -> [BODY HAS NONE OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 6 to RUN TEMPLATE 1 QUERY -> [BODY HAS 1 OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 7 to RUN TEMPLATE 1 QUERY -> [HEAD HAS ANY OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 8 to RUN TEMPLATE 1 QUERY -> [HEAD HAS NONE OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 9 to RUN TEMPLATE 1 QUERY -> [HEAD HAS 1 OF (gene_1_up,gene_10_down)]");
					System.out.println("Enter 10 to RUN TEMPLATE 2 QUERY -> [SizeOf(RULE) >= 2]");
					System.out.println("Enter 11 to RUN TEMPLATE 2 QUERY -> [SizeOf(BODY) >= 2]");
					System.out.println("Enter 12 to RUN TEMPLATE 2 QUERY -> [SizeOf(HEAD) >= 2]");
					System.out.println("Enter 13 to RUN TEMPLATE 3 QUERY -> [BODY HAS ANY OF (gene_1_up,gene_10_down) OR HEAD HAS 1 OF (gene_59_up)]");
					System.out.println("Enter 14 to RUN TEMPLATE 3 QUERY -> [BODY HAS ANY OF (gene_1_up,gene_10_down) AND HEAD HAS 1 OF (gene_59_up)]");
					System.out.println("Enter 15 to RUN TEMPLATE 3 QUERY -> [RULE HAS ANY OF (gene_1_up,gene_10_down) OR HEAD HAS NONE OF (gene_1_up)]");
					System.out.println("Enter 16 to RUN TEMPLATE 3 QUERY -> [RULE HAS ANY OF (gene_1_up,gene_10_down) AND HEAD HAS NONE OF (gene_1_up)]]");
					System.out.println("Enter 17 to RUN TEMPLATE 3 QUERY -> [BODY HAS 1 OF (gene_1_up,gene_10_down) AND SizeOf(HEAD) >= 2]");
					System.out.println("Enter 18 to RUN TEMPLATE 3 QUERY -> [BODY HAS 1 OF (gene_1_up,gene_10_down) OR SizeOf(HEAD) >= 2]");
					
					tempInput = br.readLine();
				}
				
				try{
					if( !input.equalsIgnoreCase("4") ){
						switch (input) {
							case "1":
							case "2":
								System.out.println("Please enter query:");
								String queryStr = "";
								if( isManualQuery ){
									queryStr = br.readLine();
									originalQuery = queryStr;
									queryStr = queryStr.toLowerCase();
								}
								else{
									queryStr = PreDefindedQ.qMap.get(tempInput);
									originalQuery = queryStr;
									queryStr = queryStr.toLowerCase();
								}
								
								if( queryStr.contains(" and ") || queryStr.contains(" or ") ){
									resultSet = query.query3(queryStr);
								}
								else if( queryStr.contains("sizeof") ){
									resultSet = query.query2(queryStr);
								}
								else{
									resultSet = query.query1(queryStr);
								}
								printResult(resultSet , originalQuery);
								break;
							case "3":
								resultSet.addAll(generation.getRules());
								printResult(resultSet , "All Rules");
								break;
							case "4":
								break;
							default:
								System.out.println("Invalid option\n\n");
								break;
						}
					}
				}
				catch(Exception e){
					System.out.println(e.getLocalizedMessage());
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
		catch (Exception e) {
			System.out.println("Exception");
		}
	}
	
	
	/*public static void printMatrix(ArrayList<ArrayList<String>> matrix){
		System.out.println("Matrix looks like this:");
		for(ArrayList<String> s: matrix){
			for(String x:s){
				System.out.print(x+" ");
			}
			System.out.println();
		}
	}*/
	
	
	/*
	 * this function generates items in C1, i.e., 204 items initially
	 */
	public static ArrayList<ArrayList<String>> generateCandidates(ArrayList<ArrayList<String>> matrix){
		ArrayList<String> c1 = generateC1();
		// this function generates L1 from C1
		ArrayList<ArrayList<String>> l1 = generateL1(matrix,c1);
		lCount++;
		
		System.out.println("Support >= "+MIN_SUPPORT);
		
		System.out.println("Size of frequent itemset = "+lCount+": "+l1.size());
		total += l1.size();
		
		ArrayList<ArrayList<String>> candidates=getCandidates(l1,matrix);
		candidates.addAll(l1);
		return candidates;
	}
	
	
	
	
	/**
	 * this function iterates until L is not empty and generates candidates
	 */
	public static ArrayList<ArrayList<String>> getCandidates(ArrayList<ArrayList<String>> l1, ArrayList<ArrayList<String>> matrix){
		ArrayList<ArrayList<String>> candidates = new ArrayList<ArrayList<String>>();
		for(int i=0;!l1.isEmpty();i++){
			ArrayList<ArrayList<String>> cNew=new ArrayList<ArrayList<String>>();
			for(int j=0;j<l1.size();j++){
				for(int k=j+1;k<l1.size();k++){
					ArrayList<String> first=l1.get(j);
					ArrayList<String> second=l1.get(k);
					TreeSet<String> result=new TreeSet<String>();
					result.addAll(first);
					result.addAll(second);
					ArrayList<String> res=new ArrayList<String>();
					res.addAll(result);
					if(!cNew.contains(res)){
						if(res.size()==first.size()+1){
							// this function checks whether all the subsets of cNew are present
							boolean isValidCombination=checkCombination(l1, res);
							/* cNew is generated from L and only those elements are added in cNew 
							which have size greater than items in L by 1*/
							if(isValidCombination) cNew.add(res);
						}
					}
				}
			}
			cCount++;
			// cNew generation ends
			l1=getL1(matrix,cNew);
			lCount++;
			
			System.out.println("Size of frequent itemset = "+lCount+": "+l1.size());
			total += l1.size();
			candidates.addAll(l1);
		}
		
		System.out.println("Total                       :"+total);
		return candidates;
	}
	
	
	
	static ArrayList<ArrayList<String>> getL1(ArrayList<ArrayList<String>> matrix, ArrayList<ArrayList<String>> cNew){
		ArrayList<ArrayList<String>> l1=new ArrayList<ArrayList<String>>();
		for(int i=0;i<cNew.size();i++){
			ArrayList<String> k=cNew.get(i);
			int count=0;
			for(int j=0;j<matrix.size();j++){
				if(matrix.get(j).containsAll(k)) count++;
			}
			
			if(count>=MIN_SUPPORT) l1.add(k);
		}
		return l1;
	}
	
	static boolean checkCombination(ArrayList<ArrayList<String>> l1, ArrayList<String> result){
		ArrayList<String> lNew=new ArrayList<String>();
		boolean valid=true;
		for(ArrayList<String> x:l1){
			String con="";
			for(String y:x){
				con+=y;
			}
			lNew.add(con);
		}
		for(int i=0;i<result.size();i++){
			String z="";
			for(int k=0;k<result.size();k++){
				if(i!=k){
					z+=result.get(k);
				}
			}
			if(!lNew.contains(z)) {
				valid=false;
				break;
			}
		}
		return valid;
	}
	
	static ArrayList<String> generateC1(){
		ArrayList<String> c1=new ArrayList<String>();
		for(int i=1;i<=100;i++){	// change this to 100
			c1.add("gene_"+i+"_up");
			c1.add("gene_"+i+"_down");
		}
		c1.add("A");
		c1.add("M");
		c1.add("B");
		c1.add("C");
		return c1;
	}
	
	
	
	static ArrayList<ArrayList<String>> generateL1(ArrayList<ArrayList<String>> matrix,ArrayList<String> c1){
		ArrayList<ArrayList<String>> l1=new ArrayList<ArrayList<String>>();
		for(String x:c1){
			int column=0, count=0;
			if(x.equals("A") || x.equals("M") || x.equals("B") || x.equals("C"))
				column=100;	// change this later
			else{
				int firstIndex=x.indexOf("_")+1;
				int lastIndex=x.lastIndexOf("_");
				column=Integer.parseInt(x.substring(firstIndex,lastIndex))-1;
			}
			for(int i=0;i<matrix.size();i++){
				ArrayList<String> k=matrix.get(i);
				if(x.equals(k.get(column))) {
					count++;
				}
			}
			
			if(count>=MIN_SUPPORT){
				ArrayList<String> al=new ArrayList<String>();
				al.add(x);
				l1.add(al);
			}
		}
		return l1;
	}
	
	
	public static void printResult(Set<Rule> resultSet, String query){
		System.out.println("QUERY: "+query);
		System.out.println("Number of Rules Fetched: "+resultSet.size());
		for( Rule rule: resultSet ){
			System.out.println(rule);
		}
	}
	
	
	/*static void printCandidates(ArrayList<ArrayList<String>> candidates){
	
		System.out.println();
		
		System.out.println("Totalnumber of candidates:"+candidates.size());
		System.out.println("Printing completed!");
	}*/
	
	
	/*static ArrayList<ArrayList<String>> generateTableFromFile(){
		String[][] genedata = new String[100][101];
		ArrayList<ArrayList<String>> all=new ArrayList<ArrayList<String>>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data"+File.separator+"association.dat"));
			String line = "";
					for (int i = 0 ; i <= 99 ; i++){
					line = br.readLine();
					String tempstring[] = line.split("\\t");
					ArrayList<String> al=new ArrayList<String>();
					for (int j=1; j<=101 ;j++){
						//genedata[i][j] = tempstring[j];
						String s=tempstring[j];
						if(tempstring[j].equals("UP") || tempstring[j].equals("Down")){
							if(tempstring[j].equals("UP")){
								s="gene_"+j+"_up";
							}else{
								s="gene_"+j+"_down";
							}
						}else{
							if(tempstring[j].startsWith("ALL")) s="A";
							else if(tempstring[j].startsWith("AML")) s="M";
							else if(tempstring[j].startsWith("B")) s="B";
							else if(tempstring[j].startsWith("C")) s="C";
						}
						al.add(s);
					}
					all.add(al);
				}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ArrayList<ArrayList<String>> newMatrix=new ArrayList<ArrayList<String>>();
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
	}*/
	
	
	/*static ArrayList<ArrayList<String>> generateTable(){
		ArrayList<ArrayList<String>> matrix=new ArrayList<ArrayList<String>>();
		ArrayList<String> al1=new ArrayList<String>();
		al1.add("U");
		al1.add("D");
		al1.add("U");
		al1.add("D");
		al1.add("U");
		al1.add("D");
		al1.add("U");
		al1.add("D");
		al1.add("U");
		al1.add("D");
		ArrayList<String> al2=new ArrayList<String>();
		al2.add("D");
		al2.add("U");
		al2.add("D");
		al2.add("U");
		al2.add("D");
		al2.add("U");
		al2.add("D");
		al2.add("U");
		al2.add("D");
		al2.add("U");
		ArrayList<String> al3=new ArrayList<String>();
		al3.addAll(al1);
		ArrayList<String> al5=new ArrayList<String>();
		al5.addAll(al1);
		ArrayList<String> al7=new ArrayList<String>();
		al7.addAll(al1);
		ArrayList<String> al9=new ArrayList<String>();
		al9.add("U");
		al9.add("D");
		al9.add("U");
		al9.add("D");
		al9.add("U");
		al9.add("D");
		al9.add("U");
		al9.add("D");
		al9.add("U");
		al9.add("D");
		ArrayList<String> al4=new ArrayList<String>();
		al4.addAll(al2);
		ArrayList<String> al6=new ArrayList<String>();
		al6.addAll(al2);
		ArrayList<String> al8=new ArrayList<String>();
		al8.add("U");
		al8.add("U");
		al8.add("U");
		al8.add("D");
		al8.add("D");
		al8.add("U");
		al8.add("D");
		al8.add("U");
		al8.add("D");
		al8.add("U");
		ArrayList<String> al10=new ArrayList<String>();
		al10.addAll(al9);
		al1.add("A");
		al2.add("M");
		al3.add("B");
		al4.add("C");
		al5.add("A");
		al6.add("M");
		al7.add("B");
		al8.add("C");
		al9.add("A");
		al10.add("M");
		matrix.add(al1);
		matrix.add(al2);
		matrix.add(al3);
		matrix.add(al4);
		matrix.add(al5);
		matrix.add(al6);
		matrix.add(al7);
		matrix.add(al8);
		matrix.add(al9);
		matrix.add(al10);
		ArrayList<ArrayList<String>> newMatrix=new ArrayList<ArrayList<String>>();
		for(int i=0;i<matrix.size();i++){
			ArrayList<String> k=matrix.get(i);
			ArrayList<String> a=new ArrayList<String>();
			int count=0;
			for(String s:k){
				if(s.equals("U")) a.add("gene_"+(int)(count+1)+"_up");
				else if(s.equals("D")) a.add("gene_"+(int)(count+1)+"_down");
				else a.add(s);
				count++;
			}
			newMatrix.add(a);
		}
		return newMatrix;
	}*/
}
