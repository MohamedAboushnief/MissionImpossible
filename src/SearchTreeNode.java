
public class SearchTreeNode implements Comparable {
	private String[] state;
	private SearchTreeNode parentNode;
	private String operator;
	private int depth;
	private int costToRoot;
	private int AStarCost;
	private int heuristicValue;
	private String strategy;
	private String health;
	private String CarriedPositions;
	

	public SearchTreeNode(String[] state, SearchTreeNode parentNode, String operator, int depth, int costToRoot,
			int heuristicValue, String strategy, String health, String CarriedPositions) {
		this.state = state;
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.costToRoot = costToRoot;
		this.heuristicValue = heuristicValue;
		this.strategy = strategy;
		this.health=health;
		this.CarriedPositions=CarriedPositions;
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

	public String getStrategyName() {
		return strategy;
	}

	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}
	
	public int getAStarCost() {
		return AStarCost;
	}

	public void setAStarCost(int aStarCost) {
		AStarCost = aStarCost;
	}
	

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (((SearchTreeNode) o).getStrategyName().equals("UC")) {
			return ((Integer) this.getCostToRoot()).compareTo(((SearchTreeNode) o).getCostToRoot());
		}
		else if(((SearchTreeNode) o).getStrategyName().equals("AS1") || ((SearchTreeNode) o).getStrategyName().equals("AS2")) {
			return ((Integer) this.getAStarCost()).compareTo(((SearchTreeNode) o).getAStarCost());
		}
		else {
			return ((Integer) this.getHeuristicValue()).compareTo(((SearchTreeNode) o).getHeuristicValue());
		}

	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getCarriedPositions() {
		return CarriedPositions;
	}

	public void setCarriedPositions(String carriedPositions) {
		this.CarriedPositions = carriedPositions;
	}

}