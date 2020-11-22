

public class SearchTreeNode implements Comparable{
	private String[] state;
	private SearchTreeNode parentNode;
	private String operator;
	private int depth;
	private int costToRoot;
	private int heuristicValue;

	public SearchTreeNode(String[] state, SearchTreeNode parentNode, String operator, int depth, int costToRoot, int heuristicValue) {
		this.state = state;
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.costToRoot = costToRoot;
		this.heuristicValue = heuristicValue;
		
	}

	public String[] getState() {
		return state;
	}

	public void setState(String[] state) {
		this.state = state;
	}

	public SearchTreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(SearchTreeNode parentNode) {
		this.parentNode = parentNode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getCostToRoot() {
		return costToRoot;
	}
	

	public void setCostToRoot(int costToRoot) {
		this.costToRoot = costToRoot;
	}
	
	public int getHeuristicValue() {
		return heuristicValue;
	}
	
	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}

	public int compareTo(Object node) {
		return((Integer) this.getHeuristicValue()).compareTo(((SearchTreeNode) node).getHeuristicValue());
	}
}