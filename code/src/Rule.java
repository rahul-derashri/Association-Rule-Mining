import java.util.ArrayList;


/**
 * @author RahulDerashri
 * This class will act as the rule with body head and different size parameters. 
 *
 */
public class Rule {
	ArrayList<String> body;
	ArrayList<String> head;
	ArrayList<String> all;
	Integer bodySize;
	Integer headSize;
	Integer ruleSize;
	
	public Rule(ArrayList<String> body, int bodySize, ArrayList<String> head, int headSize) {
		this.body = body;
		this.head = head;
		this.bodySize = bodySize;
		this.headSize = headSize;
		all = new ArrayList<String>();
		this.all.addAll(head);
		this.all.addAll(body);
		this.ruleSize = this.bodySize + this.headSize;
	}

	public ArrayList<String> getBody() {
		return body;
	}

	public void setBody(ArrayList<String> body) {
		this.body = body;
	}

	public ArrayList<String> getHead() {
		return head;
	}

	public void setHead(ArrayList<String> head) {
		this.head = head;
	}
	
	@Override
	public String toString() {
		return body+" --> "+head;
	}
	
	
}
