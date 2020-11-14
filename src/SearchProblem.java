import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public abstract class SearchProblem {
	
	public String BFS(SearchTreeNode initialState, String grid,SearchTreeNode goal) {
		String result = "";
		Queue<SearchTreeNode> toTraverse = new LinkedList<SearchTreeNode>();
		SearchTreeNode currentState = initialState;
		while(goal != currentState) {
			ArrayList<SearchTreeNode> stateSpace = stateTransition(currentState,grid);
			for(int i = 0; i< stateSpace.size();i++) {
				toTraverse.add(stateSpace.get(i));
			}
			currentState = toTraverse.remove();
		}
		while(currentState.getParentNode()!=null) {
			result = currentState.getOperator() + result;
			currentState = currentState.getParentNode();
		}
		return result;
		
	}
}
