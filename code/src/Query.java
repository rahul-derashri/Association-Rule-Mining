import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * @author RahulDerashri
 * 
 * This class will help parse the query and generate result set based on the query.
 *
 */
public class Query {
	String query;
	
	// Rules generated
	ArrayList<Rule> rules;
	
	public Query(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	
	/**
	 * This method process the query based on Template 1 and generate result set.
	 * 
	 * @param query : User query
	 * @return Set of Rules
	 * @throws Exception
	 */
	public Set<Rule> query1(String query) throws Exception{
		//System.out.println("Query1() called");
		Set<Rule> rs = new HashSet<Rule>();
		try{
			//System.out.println("Query: "+query);
			String[] parts = query.split(" ");
			String TAG = parts[0];
			String count = parts[2];
			String itemSet = query.substring(query.indexOf("(")+1, query.indexOf(")"));
			switch (count) {
				case "any":
					rs = query1Any(itemSet, TAG);
					break;
				case "none":
					rs = query1None(itemSet, TAG);
					break;
				default:
					rs = query1Num(itemSet, TAG, Integer.parseInt(parts[2]));
					break;
			}
		}
		catch(Exception e){
			throw new Exception("Invalid Query :(");
		}
		
		return rs;
	}
	
	
	/**
	 * This method process Template 1 queries with ANY keyword
	 * 
	 * @param itemSet: item-set from the query
	 * @param TAG: HEAD OR BODY OR RULE
	 * @return Set of Rules
	 * @throws Exception
	 */
	public Set<Rule> query1Any(String itemSet, String TAG) throws Exception{
		//System.out.println("query1Any() called");
		Set<Rule> rs = new HashSet<Rule>();
		try{
			String[] sets = itemSet.toLowerCase().split(",");
			for( Rule rule : rules ){
				boolean flag = false;
				for( String str : sets ){
					switch (TAG) {
						case "head":
							if( rule.head.contains(str.trim()) ){
								flag = true;
							}
							break;
						case "body":
							if( rule.body.contains(str.trim()) ){
								flag = true;
							}
							break;
						default:
							if( rule.all.contains(str.trim()) ){
								flag = true;
							}
							break;
					}
					
					if( flag ){
						//System.out.println(rule);
						rs.add(rule);
						break;
					}
				}
			}
		}
		catch(Exception e){
			throw new Exception("Invalid Query :(");
		}
		
		return rs;
	}
	
	
	/**
	 * This method process Template 1 queries with ANY keyword
	 * 
	 * @param itemSet: item-set from the query
	 * @param TAG: HEAD OR BODY OR RULE
	 * @return Set of Rules
	 * @throws Exception
	 */
	public Set<Rule> query1None(String itemSet, String TAG) throws Exception{
		//System.out.println("query1None() called");
		Set<Rule> rs = new HashSet<Rule>();
		
		try{
			String[] sets = itemSet.toLowerCase().split(",");
			for( Rule rule : rules ){
				int count = 0;
				for( String str : sets ){
					switch (TAG) {
						case "head":
							if( !rule.head.contains(str.trim()) ){
								count++;
							}
							break;
						case "body":
							if( !rule.body.contains(str.trim()) ){
								count++;
							}
							break;
						default:
							if( !rule.all.contains(str.trim()) ){
								count++;
							}
							break;
					}
					
					if( count == sets.length ){
						//System.out.println(rule);
						rs.add(rule);
					}
				}
			}
		}
		catch(Exception e){
			throw new Exception("Invalid Query :(");
		}
		return rs;
	}
	
	
	/**
	 * This method process Template 1 queries with Number Count
	 * 
	 * @param itemSet: item-set from the query
	 * @param TAG: HEAD OR BODY OR RULE
	 * @param num: Numerical value from the query
	 * @return Set of Rules
	 * @throws Exception
	 */
	public Set<Rule> query1Num(String itemSet, String TAG, int num) throws Exception{
		//System.out.println("query1Num() called");
		Set<Rule> rs = new HashSet<Rule>();
		
		try{
			String[] sets = itemSet.toLowerCase().split(",");
			for( Rule rule : rules ){
				int count = 0;
				for( String str : sets ){
					switch (TAG) {
						case "head":
							if( rule.head.contains(str.trim()) ){
								count++;
							}
							break;
						case "body":
							if( rule.body.contains(str.trim()) ){
								count++;
							}
							break;
						default:
							if( rule.all.contains(str.trim()) ){
								count++;
							}
							break;
					}
				}
				
				if( count == num ){
					//System.out.println(rule);
					rs.add(rule);
				}
			}
		}
		catch(Exception e){
			throw new Exception("Invalid Query :(");
		}
		return rs;
	}
	
	
	/**
	 * This method process the query based on Template 2 and generate result set.
	 * 
	 * @param query : User query
	 * @return Set of Rules
	 * @throws Exception
	 */
	public Set<Rule> query2(String query) throws Exception{
		//System.out.println("query2() called");
		Set<Rule> resultSet = new HashSet<>();
		try{
			query = query.toLowerCase();
			//System.out.println("Query: "+query);
			String TAG = query.substring(query.indexOf("(")+1, query.indexOf(")"));
			int count = Integer.parseInt(query.substring(query.indexOf("=")+1).trim());
			//System.out.println("TAG: "+TAG);
			//System.out.println("count: "+count);
			
			for( Rule rule : rules ){
				switch(TAG){
					case "head":
						if( rule.headSize >= count )
							resultSet.add(rule);
						break;
					case "body":
						if( rule.bodySize >= count )
							resultSet.add(rule);
						break;
					default:
						if( rule.ruleSize >= count )
							resultSet.add(rule);
				}
			}
		}
		catch(Exception e){
			throw new Exception("Invalid Query :(");
		}
		return resultSet;
	}
	
	
	/**
	 * This method process the query based on Template 3, split the query by "AND" or "OR"
	 * keywords and then call respective methods based on whether that follows Template1 or 
	 * Template 2 and then generate result set by applying "AND" or "OR" operators.
	 * 
	 * @param query : User query
	 * @return Set of Rules
	 * @throws Exception
	 */
	public Set<Rule> query3(String query) throws Exception{
		//System.out.println("query3() called");
		Set<Rule> r1 = new HashSet<Rule>();
		query = query.toLowerCase().trim();
		try{
			String[] queries;
			boolean isAnd = false;
			
			if( query.contains("and") ){
				queries = query.split("and");
				isAnd = true;
			}
			else if( query.contains("or") ){
				queries = query.split("or");
			}
			else{
				throw new Exception("Invalid Query :(");
			}
			
			if( queries[0].contains("sizeof") ){
				r1 = query2(queries[0].trim());
			}
			else{
				r1 = query1(queries[0].trim());
			}
			
			Set<Rule> r2 = new HashSet<Rule>();
			if( queries[1].contains("sizeof") ){
				r2 = query2(queries[1].trim());
			}
			else{
				r2 = query1(queries[1].trim());
			}
			
			if( isAnd ){
				r1.retainAll(r2);
			}
			else{
				r1.addAll(r2);
			}
		}
		catch(Exception e){
			throw new Exception("Invalid Query :(");
		}
		
		return r1;
	}
}
