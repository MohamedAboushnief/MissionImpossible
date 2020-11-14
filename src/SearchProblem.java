import java.util.ArrayList;
import java.util.Stack;

public abstract class SearchProblem {

	public static String DFS(SearchTreeNode intialState, String grid, SearchTreeNode goalState) {
		ArrayList<String[]> ancestors = new ArrayList<String[]>();
		String output = "";
		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.push(intialState);
		while (true) {
			ancestors.add(searchTreeNodesStack.peek().getState());
			output += searchTreeNodesStack.peek().getOperator();
			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.pop(), grid);
			for (int i = 0; i < x.size(); i++) {
				searchTreeNodesStack.push(x.get(i));
			}
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
				if (ancestors.contains(y.getState())) {
					searchTreeNodesStack.pop();
				} else {
					if (y.getState() == goalState.getState()) {
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
