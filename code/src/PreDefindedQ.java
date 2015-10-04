import java.util.HashMap;
import java.util.Map;


/**
 * This class stores the Default Sample Queries in a Map
 * 
 * @author RahulDerashri
 *
 */
public class PreDefindedQ {
	public static Map<String, String> qMap = new HashMap<String, String>();
	
	static {
		qMap.put("1", "RULE HAS ANY OF (gene_1_up,gene_10_down)");
		qMap.put("2", "RULE HAS NONE OF (gene_1_up,gene_10_down)");
		qMap.put("3", "RULE HAS 1 OF (gene_1_up,gene_10_down)");
		qMap.put("4", "BODY HAS ANY OF (gene_1_up,gene_10_down)");
		qMap.put("5", "BODY HAS NONE OF (gene_1_up,gene_10_down)");
		qMap.put("6", "BODY HAS 1 OF (gene_1_up,gene_10_down)");
		qMap.put("7", "HEAD HAS ANY OF (gene_1_up,gene_10_down)");
		qMap.put("8", "HEAD HAS NONE OF (gene_1_up,gene_10_down)");
		qMap.put("9", "HEAD HAS 1 OF (gene_1_up,gene_10_down)");
		qMap.put("10", "SizeOf(RULE) >= 2");
		qMap.put("11", "SizeOf(BODY) >= 2");
		qMap.put("12", "SizeOf(HEAD) >= 2");
		qMap.put("13", "BODY HAS ANY OF (gene_1_up,gene_10_down) OR HEAD HAS 1 OF (gene_59_up)");
		qMap.put("14", "BODY HAS ANY OF (gene_1_up,gene_10_down) AND HEAD HAS 1 OF (gene_59_up)");
		qMap.put("15", "RULE HAS ANY OF (gene_1_up,gene_10_down) OR HEAD HAS NONE OF (gene_1_up)");
		qMap.put("16", "RULE HAS ANY OF (gene_1_up,gene_10_down) AND HEAD HAS NONE OF (gene_1_up)");
		qMap.put("17", "BODY HAS 1 OF (gene_1_up,gene_10_down) AND SizeOf(HEAD) >= 2");
		qMap.put("18", "BODY HAS 1 OF (gene_1_up,gene_10_down) OR SizeOf(HEAD) >= 2");
		
	}
}
