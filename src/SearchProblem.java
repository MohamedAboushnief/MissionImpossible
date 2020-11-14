import java.util.ArrayList;
import java.util.Stack;

public abstract class SearchProblem {

	public static String DFS(SearchTreeNode intialState, String grid, SearchTreeNode goalState) {
		SearchTreeNode parent = null;
		String output = "";
		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.push(intialState);
		while (true) {
			output += searchTreeNodesStack.peek().getOperator();
			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.pop(), grid);
			for (int i = 0; i < x.size(); i++) {
				searchTreeNodesStack.push(x.get(i));
			}
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
				if (y == parent) {
					searchTreeNodesStack.pop();
				} else {
					if (y == goalState) {
						output += y.getOperator();
						return output;
					} else {
						break;
					}
				}
			}
		}
	}
}
